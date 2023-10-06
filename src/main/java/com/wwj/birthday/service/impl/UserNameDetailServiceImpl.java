package com.wwj.birthday.service.impl;


import com.wwj.birthday.domain.LoginUser;
import com.wwj.birthday.domain.SysUser;
import com.wwj.birthday.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 继承UserDetailsService 实现重数据库查询用户信息。
 */
@Service
public class UserNameDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名在数据库中查询用户信息
        SysUser sysUser = userMapper.selectOne(username);
        //查询角色信息
        //TODO 先写死
        List<String> permissions = new ArrayList<>(Arrays.asList("user"));
        //再封装成UserDetails实现类
        LoginUser loginUser = new LoginUser(sysUser,permissions);
        return loginUser;
    }
}
