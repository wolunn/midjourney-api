package com.wwj.handler;

import com.alibaba.fastjson.JSON;
import com.wwj.birthday.domain.AjaxResult;
import com.wwj.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 认证失败的处理器
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        AjaxResult result = AjaxResult.error(401,"认证失败，请重新登录");
        //处理异常
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
