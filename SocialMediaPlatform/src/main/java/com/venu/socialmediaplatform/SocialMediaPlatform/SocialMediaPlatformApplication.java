package com.venu.socialmediaplatform.SocialMediaPlatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages = {"com.venu.socialmediaplatform.controllers", "com.venu.socialmediaplatform.service", "com.venu.socialmediaplatform.repositories"})
@EnableJpaRepositories(basePackages = "com.venu.socialmediaplatform.repositories")
@EntityScan("com.venu.socialmediaplatform.entities")
public class SocialMediaPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialMediaPlatformApplication.class, args);
	}

}
