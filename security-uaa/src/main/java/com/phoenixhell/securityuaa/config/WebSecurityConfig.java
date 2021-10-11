package com.phoenixhell.securityuaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;

/**
 * @author phoenixhell
 * @since 2021/10/9 0009-上午 9:19
 */
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //设置授权码模式的授权码如何存取，暂时采用内存方式
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }

    //认证管理器 密码模式颁发令牌服务URL
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement() //
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) //默认创建会话
                .and()
                //允许表单登录
                .formLogin()
                     //loginPage 指定登录页面会覆盖默认登陆页面
                    //.loginPage("/loginPage")//用户未登陆时候,访问任何资源都跳转到该路径,登陆静态页面
                    //.loginProcessingUrl("/login")///登陆表单form里面 action处理表单提交地址 spring 提供 我们controller不需要提供
                    .defaultSuccessUrl("/index")//登陆成功跳转路径
                    .usernameParameter("username")//form表单中input的name名字 不改的话默认是username
                    .passwordParameter("password")//form表单中input的name名字 不改的话默认是password
                .and()
                //URL路径拦截
                .authorizeRequests()//需要登陆路径request
                    .antMatchers("/loginPage", "/login","/static/**","/oauth/**").permitAll()//不需要登陆验证就可以访问的路径 permitAll 放行
                    .antMatchers("/index").hasAnyAuthority("p1")//特别指出index需要认证并且需要p1权限才能访问
                    .anyRequest().authenticated()//其他所有路径都需要认证
                .and()
                .csrf().disable();//关闭crsf跨域攻击
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
