package com.wwj.birthday.service.impl;

import com.wwj.birthday.service.LoginService;
import com.wwj.constant.SecurityConstants;
import com.wwj.birthday.domain.AjaxResult;
import com.wwj.birthday.domain.LoginUser;
import com.wwj.birthday.domain.SysUser;
import com.wwj.utils.JwtUtils;
import com.wwj.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * 登陆接口实现类
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    @Qualifier("myAuthenticationManager")
    AuthenticationManager authenticationManager;

    @Autowired
    RedisCache redisCache;

    /**
     * 用户名密码登陆
     * @param sysUser
     * @return
     */
    @Override
    public AjaxResult login(SysUser sysUser) {
        //选则security带的token（继承Authentication）需要赋值用户名密码
        //选用不同的token，会通过循环找到token对应的provider，利用provider进行验证
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(sysUser.getUserName(),sysUser.getPassword());
        String token = this.authenticate(usernamePasswordAuthenticationToken);
        return AjaxResult.success("登录成功",token);
    }

    /**
     * 退出接口
     * @return
     */
    @Override
    public AjaxResult loginOut() {
        //获取SecurityContextHolder的用户id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        //删除redis值
        redisCache.deleteObject("login:"+loginUser.getSysUser().getUserId());
        return AjaxResult.success("退出成功");
    }

    /**
     * AuthenticationManager调用proverManager去寻找合适的验证方式去选择不同的proverder
     * return token
      */
    private String authenticate(Authentication authentication){
        Authentication authenticate = authenticationManager.authenticate(authentication);
        //认证通过，使用userID生成token，再将token返回前端
        LoginUser loginUser = (LoginUser)authenticate.getPrincipal();
        SysUser sysUser1 = loginUser.getSysUser();
        //若果认证没通过给出提示
        if(sysUser1 == null){
            throw new RuntimeException("登录失败");
        }
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(SecurityConstants.DETAILS_USER_ID,sysUser1.getUserId());
        String token = JwtUtils.createToken(claims);

        //完整的用户信息存入redis userId作为key
        redisCache.setCacheObject("login:"+sysUser1.getUserId(),loginUser,30L,TimeUnit.DAYS);

        return token;
    }
}
