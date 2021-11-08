package com.spring.boot.security.ProjectWithSecurity.service;

import com.spring.boot.security.ProjectWithSecurity.entity.Role;
import com.spring.boot.security.ProjectWithSecurity.entity.User;
import com.spring.boot.security.ProjectWithSecurity.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MainSenderService mainSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Возвращаем UserDetails - в нашем случае User
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public boolean addUser(User user){
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if(userFromDb != null){
            return false;
        }

        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER)); // Если у нас всего одно значение, а параметр принимает Set то мы используем Collections.singleton() - который создаёт Set с одним значением
        // задаём активационный код, генерируем его с помощью UUID.randomUUID().toString()
        // как только пользователь перейдёт по ссылке почта будет подтверждена
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        sendMessage(user);

        return true;
    }

    private void sendMessage(User user) {
        // отправка оповещения пользователю если у него есть почта
        // !StringUtils.isEmpty(user.getEmail()) - проверяет что строчки не равны null и не пустые
        if(!StringUtils.isEmpty(user.getEmail())){
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Estr. Please next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mainSender.send(user.getEmail(), "Activation code", message);
        }
    }

    public boolean activateUser(String code) {
        // Ищем пользователя по активационному коду в репозитории
        User user = userRepo.findByActivationCode(code);

        if(user == null){
            return false;
        }

        // Подтверждение что пользователь подтвердил почту
        user.setActivationCode(null);
        user.setActive(true);

        userRepo.save(user);

        return true;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);

        // Получаем список ролей и переводим их из Enum в String
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        // Проверяем что данная форма содержит роли для пользователя
        // И поскольку у нас в нашем списке присутствуют доп значения(токены и айдишники) то мы их отфильтруем
        for (String key :
                form.keySet()) {
            if (roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);
    }

    public void updateProfile(User user, String password, String email) {
//        String userEmail = user.getEmail();
//
//        // Получаем параметры со страницы переданные клиентом. Проверим что юзер изменил email
//        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
//                (userEmail != null && !userEmail.equals(email));
//
//        // Обновляем email у юзера если он обновился
//        if(isEmailChanged){
//            user.setEmail(email);
//
//            // Если мы обновили email то нам нужно отправить юзера новый код, для этого его нужно сгенерировать
//            if(!StringUtils.isEmpty(email)){
//                user.setActivationCode(UUID.randomUUID().toString());
//            }
//        }
//
//        // Проверяем что пользователь установил новый пароль
//        if(!StringUtils.isEmpty(password)){
//            user.setPassword(password);
//        }
//
//        userRepo.save(user);
//
//        if(isEmailChanged){
//            sendMessage(user);
//        }

        String userEmail = user.getEmail();
        String userPassword = user.getPassword();

        boolean isEmailChanged = (email != null && !email.equals(userEmail))
                || (userEmail !=null && !userEmail.equals(email));
        boolean isPasswordChanged = (password != null && !password.equals(userPassword))
                || (password !=null && !userPassword.equals(email));

        if(isEmailChanged){
            user.setEmail(email);

            if (!StringUtils.isEmpty(email)){   //если пользователь установил емаил
                user.setActivationCode(UUID.randomUUID().toString());   //то мы присваеваем новый код активации
            }
        }
        if (!StringUtils.isEmpty(password)){
            user.setPassword(password);

            if (!StringUtils.isEmpty(password)){   //если пользователь установил пароль
                user.setActivationCode(UUID.randomUUID().toString());   //то мы присваеваем новый код активации
            }
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);
        userRepo.save(user);
        if(isEmailChanged || isPasswordChanged || (isPasswordChanged && isEmailChanged)){
            sendMessage(user);
        }

    }
}
