package com.spring.boot.security.ProjectWithSecurity.controller;

import com.spring.boot.security.ProjectWithSecurity.entity.Role;
import com.spring.boot.security.ProjectWithSecurity.entity.User;
import com.spring.boot.security.ProjectWithSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
//@PreAuthorize("hasAuthority('ADMIN')") // Для каждого из методов в данном контроллере проверять перед выполнением метода наличие у пользователя прав
//// Так же нужно прописать @EnableGlobalMethodSecurity(prePostEnabled = true) в WebSecurityConfig
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model){
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    // Получем пользователя, в url передаём его id для редактирования
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userEditSave(@RequestParam String username,
                               @RequestParam Map<String, String> form,
                               @RequestParam("userId") User user) {
       userService.saveUser(user, username, form);
        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(@AuthenticationPrincipal User user,
                                @RequestParam String password,
                                @RequestParam String email){
        userService.updateProfile(user, password, email);

        return "redirect:/user/profile";
    }
}
