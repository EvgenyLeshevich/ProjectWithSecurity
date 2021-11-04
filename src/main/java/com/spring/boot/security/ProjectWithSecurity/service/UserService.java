package com.spring.boot.security.ProjectWithSecurity.service;

import com.spring.boot.security.ProjectWithSecurity.entity.Role;
import com.spring.boot.security.ProjectWithSecurity.entity.User;
import com.spring.boot.security.ProjectWithSecurity.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MainSenderService mainSender;

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

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER)); // Если у нас всего одно значение, а параметр принимает Set то мы используем Collections.singleton() - который создаёт Set с одним значением
        // задаём активационный код, генерируем его с помощью UUID.randomUUID().toString()
        // как только пользователь перейдёт по ссылке почта будет подтверждена
        user.setActivationCode(UUID.randomUUID().toString());

        userRepo.save(user);

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

        return true;
    }

    public boolean activateUser(String code) {
        // Ищем пользователя по активационному коду в репозитории
        User user = userRepo.findByActivationCode(code);

        if(user == null){
            return false;
        }

        // Подтверждение что пользователь подтвердил почту
        user.setActivationCode(null);

        userRepo.save(user);

        return true;
    }
}
