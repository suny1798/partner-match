package com.yupi.patrentmatch.service;

import org.junit.jupiter.api.Test;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

//@SpringBootTest
public class RedissonTest {

    @Resource
    private RedissonClient redissonClient;

    @Test
    void test(){

        RList<String> rList = redissonClient.getList("suny");
        rList.add("sun");
        rList.add("sun1");
        rList.add("sun2");
        rList.get(0);
        System.out.println(rList.get(0));
    }

}
