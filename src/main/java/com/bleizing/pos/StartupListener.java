package com.bleizing.pos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.bleizing.pos.model.User;
import com.bleizing.pos.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StartupListener implements ApplicationListener<ApplicationReadyEvent> {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
		log.info("Startup Listener");
		User user = User.builder().email("tes@tes.com").build();
		userRepository.save(user);
    }
}
