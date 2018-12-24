package com.eim.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
/**
 * redis 分布式锁
 * Created by Zy on 2018/12/20.
 */
@Slf4j
@Component
public class RedisLock {

    //redis操作模板类
//    @Autowired
    @Autowired(required = true)
    private StringRedisTemplate redisTemplate;

    /**
     *上锁
     * @param key  键值（productId）
     * @param value 当前时间+余留时间=超时时间
     * @return
     */
    public boolean lock(String key,String value){
        /**
         * setnx 当key存在设置失败，key不存在设置成功
         *setIfAbsent()---->setnx 设置成功返回true,失败返回false
         */
        if(redisTemplate.opsForValue().setIfAbsent(key,value)){
            return true;
        }
        /**
         * 设置失败
         * 防止死锁（解锁未成功），和多线程访问
         * 通过key 获取当前值
         */
        String currentValue = redisTemplate.opsForValue().get(key);
        //判断锁是否过期 ---作用防止死锁
        if(!StringUtils.isEmpty(currentValue) &&
                Long.parseLong(currentValue) < System.currentTimeMillis()){//如果过期
            //获取上一个锁的时间（value）
            //getAndSet  先获取值返回，再设置值
            //此时，oldValue和 currentValue因为多线程原因可能会相同或者不同
            String oldValue = redisTemplate.opsForValue().getAndSet(key,value);
            //如果一致
            if(!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)){
                return true;
            }
        }

        //失败返回false
        return false;
    }

    /**
     * 解锁
     * @param key（productId）
     * @param value 当前时间+余留时间=超时时间
     * @return
     */
    public void unlock(String key,String value){
        try {
            String currentValue = redisTemplate.opsForValue().get(key);
            if(!StringUtils.isEmpty(currentValue) && currentValue.equals(value)){
                //删除键值
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e){
            log.error("【解锁异常】");
        }
    }

}
