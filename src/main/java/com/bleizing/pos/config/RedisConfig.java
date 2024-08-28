package com.bleizing.pos.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

	@Value("${spring.redis.host}")
    private String host;
	
	@Value("${spring.redis.port}")
    private int port;
	
	@Value("${spring.redis.database}")
    private int database;
	
	@Value("${spring.redis.password}")
    private String password;
	
	@Bean
	RedisConnectionFactory redisConnectionFactory() throws IOException {
		LettuceConnectionFactory lcf = new LettuceConnectionFactory();
        lcf.setHostName(host);
        lcf.setPort(port);		
        lcf.setDatabase(database);
        lcf.setPassword(password);
        lcf.afterPropertiesSet();
        return lcf;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() throws IOException {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory());
		return template;
	}
}
