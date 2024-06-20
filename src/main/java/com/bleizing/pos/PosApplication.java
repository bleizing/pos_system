package com.bleizing.pos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.bleizing.pos.model.User;
import com.bleizing.pos.repository.UserRepository;

@SpringBootApplication
@EnableJpaAuditing
public class PosApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(PosApplication.class, args);
	}
}
