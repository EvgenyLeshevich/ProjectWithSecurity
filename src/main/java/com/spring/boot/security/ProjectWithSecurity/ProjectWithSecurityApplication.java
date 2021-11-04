package com.spring.boot.security.ProjectWithSecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//    @EnableAsync // Для быстрой отправки почти вроде
public class ProjectWithSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectWithSecurityApplication.class, args);
	}

}
