package com.spring.boot.security.ProjectWithSecurity.controller;

import com.spring.boot.security.ProjectWithSecurity.entity.Role;
import com.spring.boot.security.ProjectWithSecurity.entity.User;
import com.spring.boot.security.ProjectWithSecurity.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
//@PreAuthorize("hasAuthority('ADMIN')") // Для каждого из методов в данном контроллере проверять перед выполнением метода наличие у пользователя прав
// Так же нужно прописать @EnableGlobalMethodSecurity(prePostEnabled = true) в WebSecurityConfig
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public String userList(Model model){
        model.addAttribute("users", userRepo.findAll());
        return "userList";
    }

    // Получем пользователя, в url передаём его id для редактирования
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    // Сохранение после изменений пользователя
    // @RequestParam("userId") User user - получим пользователя по его id
    // @RequestParam Map<String, String> form - получим список полей который передаются в этой форме, так как у нас изменяемое
    // количество полей они все будут попадать в форму но на сервер будут приходить только те которые отмечены флажком
    @PostMapping
    public String userEditSave(@RequestParam String username,
                               @RequestParam Map<String, String> form,
                               @RequestParam("userId") User user) {
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
        return "redirect:/user";
    }
}
