package com.wwj.handler;

import com.alibaba.fastjson.JSON;
import com.wwj.birthday.domain.AjaxResult;
import com.wwj.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权失败处理器
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        AjaxResult result = AjaxResult.error(403,"授权失败，无权限访问");
        //处理异常
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
