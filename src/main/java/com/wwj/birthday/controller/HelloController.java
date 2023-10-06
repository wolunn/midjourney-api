package com.wwj.birthday.controller;

import com.wwj.birthday.domain.LoginUser;
import com.wwj.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private RedisCache redisCache;

    @GetMapping("count")
    public String count(){
        if(!redisCache.hasKey("count")){
            redisCache.setCacheObject("count",1);
        }
        Integer count = redisCache.getCacheObject("count");
        redisCache.setCacheObject("count",count+1);
        return  "";
    }

    @GetMapping("hello1")
    //@PreAuthorize("hasAuthority('admin')")
    @PreAuthorize("@ex.hasAuthority('admin')")
    public String hello1(){
        return  "hello1";
    }

    @GetMapping("hello2")
    public String hello2(){
        return  "hello2";
    }

}
