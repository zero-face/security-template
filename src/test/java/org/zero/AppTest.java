package org.zero;


import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.zero.entity.User;



/**
 * Unit test for simple App.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void shouldAnswerWithTrue() {
        System.out.println(redisTemplate);
//        redisTemplate.opsForValue().set("user", new User());
//        System.out.println(redisTemplate.opsForValue().get("user"));
//        redisTemplate.opsForHash().put("ceshi", "hello", "java");
//        System.out.println(redisTemplate.opsForHash().get("ceshi", "htllo");
        System.out.println(redisTemplate.opsForValue().get("tt"));

    }
}
