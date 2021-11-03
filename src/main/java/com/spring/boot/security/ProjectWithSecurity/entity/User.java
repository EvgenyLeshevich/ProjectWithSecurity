package com.spring.boot.security.ProjectWithSecurity.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private boolean active;

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
}
