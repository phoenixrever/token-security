package com.phoenixhell.securityuaa.config;

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
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author phoenixhell
 * @since 2021/10/11 0011-下午 2:26
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private TokenStore tokenStore;

    //以下4个实例在WebSecurityConfig中配置了bean
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JdbcClientDetailsService jdbcClientDetailsService;
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;



    /**
     * 客户端详情   配置 ClientDetailsService
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory() //使用in‐memory存储
//                .withClient("client1")//client_id 客户端id
//                .secret(new BCryptPasswordEncoder().encode("secret")) //客户端密钥
//                .resourceIds("resource1")//客户端可以访问的资源列表
//                //该client允许的授权类型 authorization_code, password, refresh_token, implicit, client_credentials
//                .authorizedGrantTypes("authorization_code",
//                        "password", "client_credentials", "implicit", "refresh_token")
//                .scopes("all")//read 等等允许的授权范围 all不是所有也是一种标识
//                .autoApprove(false)//跳转到授权页面  true 不用跳转直接发令牌
//                .redirectUris("http://www.baidu.com")//加上验证回调地址
//                .and()
//                .withClient("client2")
//                .secret(new BCryptPasswordEncoder().encode("secret2"))
//                .resourceIds("resource2")//
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
        tokenServices.setTokenStore(tokenStore);//令牌存储策略
        //令牌增强(转化为jwt令牌)
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter));
        tokenServices.setTokenEnhancer(tokenEnhancerChain);

        tokenServices.setAccessTokenValiditySeconds(60*60*2);//令牌默认有效期2小时
        tokenServices.setRefreshTokenValiditySeconds(60*60*24*3);//刷新令牌默认有效期3天
        return tokenServices;
    }



    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)//认证管理器  密码模式颁发令牌服务URL 开启
                .authorizationCodeServices(authorizationCodeServices)//授权码模式颁发令牌服务URL开启
                .tokenServices(tokenServices())//令牌存储管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);//允许post提交访问令牌
    }

    //令牌访问端点
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
                //允许表单认证
                .allowFormAuthenticationForClients();
    }
}
