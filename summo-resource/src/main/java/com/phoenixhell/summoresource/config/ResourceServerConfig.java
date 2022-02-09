package com.phoenixhell.summoresource.config;

import com.phoenixhell.summoresource.handler.CustomAccessDeniedHandler;
import com.phoenixhell.summoresource.handler.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author phoenixhell
 * @since 2021/10/12 0012-上午 9:09
 */

@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    //对应  AuthorizationServer 里面 配置的 client 详细配置 resourceIds("resource1")客户端可以访问的资源列表
    public static final String RESOURCE_ID="resource2";

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private TokenStore tokenStore;


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID)//客户端可以访问的资源列表
                //.tokenStore(tokenStore)// 资源服务和授权服务在一个工程里面可以本地内存存储和验证令牌(通常情况下不在)
                //.tokenServices(tokenService())//远程验证令牌的服务配置jwt令牌后停用 使用本地校验jwt
                .tokenStore(tokenStore)// 基于jwt令牌的校验
                .stateless(true);
                //其他资源令牌验证
                //.resourceId(RESOURCE_ID2)
                //.tokenServices(tokenService())

    }


    //HttpSecurity配置这个与Spring Security类似：
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //基于token 不创建和使用session
                .and()
                //URL路径拦截 oauth不需要在这放行
                .authorizeRequests()//需要登陆路径request
                .antMatchers("/summoresource/house/**","/favicon.ico").permitAll()
                .antMatchers("/**").access("#oauth2.hasScope('all')")// 校验令牌的访问范围(scope) 是不是all
                .anyRequest().authenticated()//其他所有路径都需要认证
                .and()
                .csrf().disable();//关闭crsf跨域攻击
    }


    /**
     * 调用远程认证服务解析普通令牌 /oauth/check_token
     * jwt令牌在本地验证 不需要远程验证了
     */
    public ResourceServerTokenServices tokenService(){
        //使用远程服务请求授权服务器校验token,必须知道token的url,client_id,client_secret
        RemoteTokenServices services=new RemoteTokenServices();
        services.setCheckTokenEndpointUrl("http://localhost:10001/oauth/check_token");
        services.setClientId("client1");
        services.setClientSecret("secret");
        return services;
    }
}
