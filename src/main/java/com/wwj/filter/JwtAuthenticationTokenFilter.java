package com.wwj.filter;

import com.wwj.constant.SecurityConstants;
import com.wwj.birthday.domain.LoginUser;
import com.wwj.utils.JwtUtils;
import com.wwj.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)){
            //放行
            filterChain.doFilter(request,response);
            return;
        }

        //解析token
        Claims claims = JwtUtils.parseToken(token);
        String userId = claims.get(SecurityConstants.DETAILS_USER_ID).toString();

        //根据userId在redis中获取用户信息
        LoginUser loginUser = redisCache.getCacheObject("login:" + userId);

        if(loginUser == null){
            throw new RuntimeException("用户未登录");
        }

        //存入SecurityContex上下文
        //TODO 获取权限信息
        UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(userToken);

        //放行
        filterChain.doFilter(request,response);
    }
}
