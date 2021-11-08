package com.spring.boot.security.ProjectWithSecurity.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String username;
    @NotEmpty(message = "Password should not be empty")
    private String password;
    @Transient // что бы хибернэйт не создавал такое поле в таблице бд
//    @NotEmpty(message = "Password confirmation should not be empty")
    private String passwordTwo;
    private boolean active;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Please enter a valid e-mail address")
    private String email;
    private String activationCode;

    // @ElementCollection - формирует сам дополнительную таблицу по хранению Enum
    // fetch - это параметр который определяет как данные значения будут подгружаться относительно основной сущности
    // когда мы загружаем пользователя его роли мы храним в отдельной таблице
    // FetchType.EAGER - жадный способ подгрузки ролей, хибернэйт будет сразу при запросе пользователя подгружать все его роли
    // FetchType.LAZY - ленивый подгрузит роли только когда пользователь обратится к этому полю
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    // @CollectionTable - описывает что данное поле будет храниться в отдельной таблице для которой мы не описывали мапинг
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    // @Enumerated(EnumType.STRING) - храним enum в виде строки
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    // Проверяем является ли пользователь админом
    public boolean isAdmin(){
        return roles.contains(Role.ADMIN);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getPasswordTwo() {
        return passwordTwo;
    }

    public void setPasswordTwo(String passwordTwo) {
        this.passwordTwo = passwordTwo;
    }
}
