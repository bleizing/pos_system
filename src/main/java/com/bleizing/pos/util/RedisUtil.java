package com.bleizing.pos.util;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@Service
public class RedisUtil {
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Resource(name="redisTemplate")
	private HashOperations<String, Object, Object> hashOperations;
	
	@Value("${redis.timeout}")
    private long timeout;
	
	public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }
	
	public void setValue(String key, String value) {
		setValue(key, value, timeout);
    }
	
	public void setValue(String key, String value, long timeout) {
		setValue(key, value, timeout, TimeUnit.SECONDS);
    }
	
	public void setValue(String key, String value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().setIfAbsent(key, value, timeout, timeUnit);
    }
	
	public Object getOps(String category, String key) {
        return hashOperations.get(category, key);
    }
	
	public void setOps(String category, String key, Object value) {
		hashOperations.putIfAbsent(category, key, value);
    }
}
