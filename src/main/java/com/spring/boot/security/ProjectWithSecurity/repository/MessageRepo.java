package com.spring.boot.security.ProjectWithSecurity.repository;

import com.spring.boot.security.ProjectWithSecurity.entity.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepo extends CrudRepository<Message, Long> {
    List<Message> findByTag(String tag);
}
