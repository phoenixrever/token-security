package com.phoenixhell.securityuaa.config;

import com.phoenixhell.securityuaa.service.impl.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author phoenixhell
 * @since 2021/10/11 下午 2:26
 *
 * 配置认证服务器
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private TokenStore jwtTokenStore;

    //以下4个实例在WebSecurityConfig中配置了bean
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JdbcClientDetailsService jdbcClientDetailsService;
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private MyUserDetailService myUserDetailService;



    /**
     * 配置查找客户端详细信息
     * ClientDetailsServiceConfigurer 能够使用内存或者JDBC来实现客户端详情服务（ClientDetailsService），
     * ClientDetailsService负责查找ClientDetails，而ClientDetails有几个重要的属性如下列表：
     * 	• clientId ：（必须的）用来标识客户的Id。
     * 	• secret ：（需要值得信任的客户端）客户端安全码，如果有的话。
     * 	• scope ：用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
     * 	• authorizedGrantTypes ：此客户端可以使用的授权类型，默认为空。
     * 	• authorities ：此客户端可以使用的权限（基于Spring Security authorities）。
     *
     * 客户端详情（Client Details）能够在应用程序运行的时候进行更新
     * 可以通过访问底层的存储服务（例如将客户端详情存储在一个关系数据库的表中
     * 就可以使用 JdbcClientDetailsService）或者通过自己实现ClientRegistrationService接口
     * （同时你也可以实现 ClientDetailsService 接口）来进行管理。
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory() //使用in‐memory存储
//                .withClient("web")//client_id 客户端id
//                .secret(new BCryptPasswordEncoder().encode("secret"))//客户端密钥
//                .accessTokenValiditySeconds(36000) //令牌token有效期 单位 秒
//                .resourceIds("resource1")//客户端可以访问的资源列表
//                //该client允许的授权类型 authorization_code, password, refresh_token, implicit, client_credentials
//                .authorizedGrantTypes("authorization_code",
//                        "password", "client_credentials", "implicit", "refresh_token")
//                .scopes("all")// 用来限制客户端的访问范围 all不是所有也是一种标识
//                .autoApprove(false)//跳转到授权页面  true 不用跳转直接发令牌
//                .redirectUris("http://www.baidu.com")//加上验证回调地址 授权成功后的跳转地址
//                .and()
//                 //配置多个客户端
//                .withClient("client2")
//                .secret(new BCryptPasswordEncoder().encode("secret2"))
//                .resourceIds("resource2")
//                .authorizedGrantTypes("authorization_code",
//                        "password", "client_credentials", "implicit", "refresh_token")
//                .scopes("all")
//                .autoApprove(false)
//                .redirectUris("http://www.baidu.com");
        //换到数据库存储用户详细信息  JdbcClientDetailsService  或者自己实现ClientRegistrationService 接口
        clients.withClientDetails(jdbcClientDetailsService);
    }


    /**
     * 令牌管理服务
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setClientDetailsService(jdbcClientDetailsService);//客户端信息服务
        tokenServices.setSupportRefreshToken(true);//是否产生刷新令牌
        tokenServices.setTokenStore(jwtTokenStore);//令牌存储策略
        //令牌增强(返回值放入更多内容)
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        //customTokenEnhancer() 自定义返回token内容
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(customTokenEnhancer(),jwtAccessTokenConverter));
        tokenServices.setTokenEnhancer(tokenEnhancerChain);

        tokenServices.setAccessTokenValiditySeconds(60*60*2);//令牌默认有效期2小时
        tokenServices.setRefreshTokenValiditySeconds(60*60*24*3);//刷新令牌默认有效期3天
        return tokenServices;
    }


    /**
     * 使用密码模式所需配置
     * AuthorizationServerEndpointsConfigurer 通过设定以下属性决定支持的授权类型（Grant Types）:
     *      authenticationManager:密码（password）授权类型的时候需要设置这个属性注入一个 AuthenticationManager 对象(用于比对用户输入密码和数据密码)
     *      userDetailsService : 获取数据库用户信息,设置这个的话 refresh_token 时候也查看账号是否仍然有效(没有被禁用)
     *      authorizationCodeServices :设置 authorization_code 授权码类型模式
     *      implicitGrantService : 设置隐式授权模式，用来管理隐式授权模式的状态
     *      tokenGranter: 完全掌控授权，并且会忽略掉上面的这几个属性，这个属性一般是用作拓展用途的(四种授权模式都不符合)
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)//认证管理器  开启    密码模式颁发令牌服务URL
                //.authorizationCodeServices(authorizationCodeServices)//开启授权码模式颁发令牌服务URL
                .userDetailsService(myUserDetailService) //refresh_token 查看令牌是否被禁用(校验用户名密码在ifilter里面也会做 这边不太清楚)
                .tokenServices(tokenServices())//令牌存储管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST)//允许post提交访问令牌
                //自定义获取token等校验 地址
                .pathMapping("/oauth/token", "/securityuaa/oauth/token")
                .pathMapping("/oauth/check_token", "/securityuaa/oauth/check_token");
    }

    /**
     * 令牌端点的安全约束 (默认配置即可)
     * 	• /oauth/authorize ：授权端点。
     * 	• /oauth/token ：令牌端点。
     * 	• /oauth/confirm_access ：用户确认授权提交端点。
     * 	• /oauth/error ：授权服务错误信息端点。
     * 	• /oauth/check_token ：用于资源服务访问的令牌解析端点。
     *  • /oauth/token_key ：提供公有密匙的端点，如果你使用JWT令牌的话
     *
     *  （1）tokenkey这个endpoint url 当使用JwtToken且使用非对称加密时，资源服务用于获取公钥而开放的，这里指这个endpoint完全公开(无需授权即可访问)
     *       目前我使用的是对称加密 用不到
     *  （2）checkToken这个endpoint完全公开 (无需授权即可访问)
     *  （3） 允许表单认证
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                //端点  就是URL  限制颁发令牌的URl 的访问权限 哪些人可以访问
                //tokenkey这个endpoint当使用JwtToken且使用非对称加密时，资源服务用于获取公钥而开放的，
                //暴露 /oauth/token_key 这个url 并允许访问
                .tokenKeyAccess("permitAll()")
                // 使用授权服务的 /oauth/check_token 端点(url)你需要在授权服务将这个端点暴露出去，
                // 以便资源服务可以进行访问
                // 简单来说就是暴露 /oauth/check_token 这个url 并允许访问
                .checkTokenAccess("permitAll()")
                //允许表单认证(用户名密码登陆不能少)
                .allowFormAuthenticationForClients();
    }

    @Bean
    public TokenEnhancer customTokenEnhancer() {
        return new CustomTokenConverter();
    }
}
