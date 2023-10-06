package com.wwj.birthday.service;

import com.wwj.birthday.domain.AjaxResult;
import com.wwj.birthday.domain.SysUser;

public interface LoginService {

    AjaxResult login(SysUser sysUser);

    AjaxResult loginOut();
}
