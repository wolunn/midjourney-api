package com.wwj.birthday.controller;

import com.wwj.birthday.domain.AjaxResult;
import com.wwj.birthday.domain.SysUser;
import com.wwj.birthday.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 登陆接口
 */
@RestController
public class LoginController {


    @Autowired
    LoginService loginService;

    /**
     * 用户名密码登陆
     * @param sysUser
     * @return
     */
    @PostMapping("/login/username")
    public AjaxResult userLogin(@RequestBody SysUser sysUser) {
        return loginService.login(sysUser);
    }

    /**
     * 退出登录
     * @return
     */
    @GetMapping("/loginOut")
    public AjaxResult LoginOut() {
        return loginService.loginOut();
    }
}
