package com.spring.boot.security.ProjectWithSecurity.controller;

import com.spring.boot.security.ProjectWithSecurity.entity.User;
import com.spring.boot.security.ProjectWithSecurity.entity.dto.CaptchaResponseDto;
import com.spring.boot.security.ProjectWithSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class RegistrationController {
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    private UserService userService;

    @Value("${recaptcha.secret}")
    private String secret;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") User user){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam("g-recaptcha-response") String captchaResponce,
                          @ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult,
                          Model model) {
        String url = String.format(CAPTCHA_URL, secret, captchaResponce);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        if(!response.isSuccess()){
            model.addAttribute("captchaError", "Fill captcha");
        }

        if(user.getPassword() != null && !user.getPassword().equals(user.getPasswordTwo())){
            model.addAttribute("passwordTwoError", "Passwords are different!");
            return "registration";
        }
        if (bindingResult.hasErrors() || !response.isSuccess()){
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
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found");
        }

        return "login";
    }
}
