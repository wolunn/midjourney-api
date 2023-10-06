package com.wwj.birthday.mapper;


import com.wwj.birthday.domain.SysUser;

public interface UserMapper {
    SysUser selectOne(String username);
}
