package com.spring.boot.security.ProjectWithSecurity.controller;

import com.spring.boot.security.ProjectWithSecurity.entity.User;
import com.spring.boot.security.ProjectWithSecurity.repository.UserRepo;
import com.spring.boot.security.ProjectWithSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") User user){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model){
        if(user.getPassword() != null && !user.getPassword().equals(user.getPasswordTwo())){
            model.addAttribute("passwordTwoError", "Passwords are different!");
            return "registration";
        }
        if (bindingResult.hasErrors()){
            return "registration";
        }
        else {
            // Если не смогли добавить пользователя значит такой уже существует
            if(!userService.addUser(user)){
                model.addAttribute("message", "User exists!");
                return "registration";
            }
            return "redirect:/login";
        }
    }

    // Обработка подтверждения аккаунта
    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActivated = userService.activateUser(code);

        if(isActivated){
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("message", "Activation code is not found");
        }

        return "login";
    }
}
