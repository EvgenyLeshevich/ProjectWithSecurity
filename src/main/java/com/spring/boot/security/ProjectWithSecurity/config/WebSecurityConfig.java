package com.spring.boot.security.ProjectWithSecurity.config;

import com.spring.boot.security.ProjectWithSecurity.entity.User;
import com.spring.boot.security.ProjectWithSecurity.repository.UserRepo;
import com.spring.boot.security.ProjectWithSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder(8);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/registration", "/static/**", "/activate/*", "/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .rememberMe() // даже если сессия в контейнере (tomcat) истекла то спринг всё равно будет искать по полученным от клиента идентификаторам наши настройки и попытается авторизовать клиента повторно (не будет работать например при перезапуске приложения), информация скорей всего будет потеряна, но можно хранить её отдельно во внешних кэшах(редис и тд), а можно в бд
                .and()
                .logout()
                .permitAll();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    // здесь мы берём пользователя из базы данных для допуска их к авторизации
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);

        User admin = userRepo.findByUsername("admin");
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        userRepo.save(admin);
    }
}