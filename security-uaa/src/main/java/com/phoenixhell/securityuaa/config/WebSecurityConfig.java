package com.phoenixhell.securityuaa.config;

import com.phoenixhell.securityuaa.handler.CustomAccessDeniedHandler;
import com.phoenixhell.securityuaa.handler.CustomAuthenticationEntryPoint;
import com.phoenixhell.securityuaa.handler.CaptchaFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

/**
 * @author phoenixhell
 * @since 2021/10/9 0009-上午 9:19
 */
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Value("${security.baseUrl}")
    private String baseUrl;


    //客户端详情数据库存储   这里不自己实现接口 直接用 JdbcClientDetailsService
    //clientDetailsService 名字已经被使用 或者使用覆盖设置 或者改个名字
    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService(DataSource dataSource) {
        JdbcClientDetailsService clientDetailsService =  new JdbcClientDetailsService(dataSource);
        clientDetailsService.setPasswordEncoder(passwordEncoder());
        return clientDetailsService;
    }


    //设置授权码模式的授权码如何存取，数据库存储
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        return new JdbcAuthorizationCodeServices(dataSource);
    }


    // 密码模式颁发令牌服务 需要 调用AuthenticationManager.authenticate
    // 比对用户输入的密码和userdetails查询数据库得出的密码
    // 这个不能AuthorizationServer里面  会没有super.authenticationManager()方法
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    /**
     *  授权服务器需要配置拦截路径
     *  资源管理器自带拦截路径就不需要配置了
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //定义的一般过滤器 一般无需在spring security 注册
        //但是最好在这注册下 放在UsernamePasswordAuthenticationFilter前面
         //http.addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);
        http
                //自定义异常处理
                .exceptionHandling()
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .accessDeniedHandler(customAccessDeniedHandler)
                .and()
                .sessionManagement() //
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //不创建会话
                .and()
                .authorizeRequests()
                    //.antMatchers("/securityuaa/menu/**","/securityuaa/auth/**","/securityuaa/user/**","/favicon.ico").permitAll()//需要登陆路径request
                    .antMatchers("/securityuaa/auth/**","/favicon.ico").permitAll()//需要登陆路径request
                    .antMatchers("/securityuaa/user/**").permitAll()
                    .anyRequest().authenticated()//其他所有路径都需要认证
                .and()
                .csrf().disable();//关闭crsf跨域攻击
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
