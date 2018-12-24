package com.eim.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Zy on 2018/12/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisLockTest {

    @Autowired
    private RedisLock redisLock;

    @Test
    public void lock() throws Exception {

        //上锁
        long l = System.currentTimeMillis() + 10 * 1000;
        if(redisLock.lock("sdfd",String.valueOf(l))){
            System.out.println("处理业务逻辑");
        }
        //解锁
        redisLock.unlock("sdfd",String.valueOf(l));

    }


}