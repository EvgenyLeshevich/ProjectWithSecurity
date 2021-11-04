package com.spring.boot.security.ProjectWithSecurity.repository;

import com.spring.boot.security.ProjectWithSecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByActivationCode(String code);
}
