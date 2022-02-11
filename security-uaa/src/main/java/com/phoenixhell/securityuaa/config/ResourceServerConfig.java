package com.phoenixhell.securityuaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 *
 * 	ResourceServerSecurityConfigurer 资源服务器:
 * 	    • tokenServices ：ResourceServerTokenServices 类的实例，用来实现令牌服务。
 * 	    • tokenStore ：TokenStore类的实例，指定令牌如何访问，与tokenServices配置可选
 *      • resourceId ：这个资源服务的ID，这个属性是可选的，但是推荐设置并在授权服务中进行验证。
 *
 *  拓展属性例如 tokenExtractor 令牌提取器用来提取请求中的令牌。
 *
 *  @EnableResourceServer 注解自动增加了一个类型为 OAuth2AuthenticationProcessingFilter 的过滤器链
 */

@EnableResourceServer
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    //对应  AuthorizationServer 里面 配置的 client 可以访问的资源 id resourceIds("resource1")
    //客户端可以访问的资源列表
    public static final String RESOURCE_ID="web";

    @Autowired
    private TokenStore jwtTokenStore;


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID)//客户端可以访问的资源列表
                //.tokenStore(tokenStore)// 资源服务和授权服务在一个工程里面可以本地内存存储和验证令牌(通常情况下不在)
                //.tokenServices(tokenService())//远程验证令牌的服务配置jwt令牌后停用 使用本地校验jwt
                .tokenStore(jwtTokenStore)// 基于jwt令牌的校验
                .stateless(true);
                //其他资源令牌验证
                //.resourceId(RESOURCE_ID2)
                //.tokenServices(tokenService())

    }


    /**
     * HttpSecurity配置   与Spring Security类似 资源的放行
     *  授权服务器需要配置拦截路径
     *  资源管理器自带拦截路径就不需要配置了
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //基于token 不创建和使用session
                .and()

                .authorizeRequests()
                //.antMatchers("/securityuaa/auth/**").permitAll() //URL路径拦截 oauth不需要在这放行
                .antMatchers("/securityuaa/user/**").permitAll()
                .antMatchers("/**").access("#oauth2.hasScope('all')")// 校验令牌的访问范围(scope) 是不是all
                .anyRequest().authenticated()//其他所有路径都需要认证
                .and()
                .csrf().disable();//关闭crsf跨域攻击防护
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
