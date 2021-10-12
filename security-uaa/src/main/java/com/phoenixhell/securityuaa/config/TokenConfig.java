package com.phoenixhell.securityuaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author phoenixhell
 * @since 2021/10/11 0011-下午 3:13
 */
@Configuration
public class TokenConfig {
    //对称秘钥，资源服务器使用该秘钥来验证
   private static final String TOKEN_KEY="key";

    @Bean
    public TokenStore tokenStore(){
       // return new InMemoryTokenStore();  //内存令牌存储策咯
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        //对称秘钥，资源服务器使用该秘钥来验证
        accessTokenConverter.setSigningKey(TOKEN_KEY);
        return accessTokenConverter;
    }
}
