package com.spring.boot.security.ProjectWithSecurity.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name(); // Строковое представление USER
    }
}
