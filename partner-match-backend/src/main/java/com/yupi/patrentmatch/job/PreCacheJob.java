package com.yupi.patrentmatch.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.patrentmatch.model.domain.User;
import com.yupi.patrentmatch.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PreCacheJob {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private UserService userService;

    @Resource
    private RedissonClient redissonClient;

    private List<Long> userList = Arrays.asList(2L);

    /**
     * 定时任务
     * 每天执行
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void doCacheReconmmendUser(){
        //Redisson异步锁
        RLock lock = redissonClient.getLock("partner:doCache:lock");
        try {
            if(lock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {//等待时间、过期时间-1:自动续期
                log.info("获取锁成功"+ Thread.currentThread().getId());
                for (Long userId : userList) {
                    String redisKey = String.format("partner:user:recommend:%s", userId);
                    ValueOperations valueOperations = redisTemplate.opsForValue();
                    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                    List<User> userList = userService.list(queryWrapper);
                    List<User> list = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
                    //写缓存
                    valueOperations.set(redisKey, list, 1, TimeUnit.HOURS);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            //防止释放掉被人的锁
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
                log.info("释放锁"+ Thread.currentThread().getId());
            }
        }
    }
}
