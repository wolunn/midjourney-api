package com.wwj.config;

import com.wwj.filter.JwtAuthenticationTokenFilter;
import com.wwj.handler.AccessDeniedHandlerImpl;
import com.wwj.handler.AuthenticationEntryPointImpl;
import com.wwj.birthday.service.impl.UserNameDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    //用户名密码登录
    private UserNameDetailServiceImpl userNameDetailServiceImpl;

    @Autowired
    //token过滤器
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    //认证失败处理器
    private AuthenticationEntryPointImpl authenticationEntryPointImpl;

    @Autowired
    //授权失败处理器
    private AccessDeniedHandlerImpl accessDeniedHandlerImpl;

    /**
     * 配置provider(DaoAuthenticationProvider) 用户名密码的一套
     * @return
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userNameDetailServiceImpl);
        return daoAuthenticationProvider;
    }


    /**
     * 创建密码加密方式BCryptPasswordEncoder注入容器中
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 定义认证管理器
     * @return
     * @throws Exception
     */
    @Override
    @Bean(name="myAuthenticationManager")
    public AuthenticationManager authenticationManagerBean() throws Exception {
        ProviderManager authenticationManager = new ProviderManager(Arrays.asList(daoAuthenticationProvider()));
        //不擦除认证密码，擦除会导致TokenBasedRememberMeServices因为找不到Credentials再调用UserDetailsService而抛出UsernameNotFoundException
        authenticationManager.setEraseCredentialsAfterAuthentication(false);
        return authenticationManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //登录接口允许匿名访问
                .antMatchers("/login/**","/count")
                .anonymous()
                .anyRequest().authenticated();

        http
                //添加token认证的过滤器到security的过滤链前
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http
                //配置认证失败处理器和授权失败处理器
                .exceptionHandling().
                authenticationEntryPoint(authenticationEntryPointImpl)
                .accessDeniedHandler(accessDeniedHandlerImpl);
    }

}
