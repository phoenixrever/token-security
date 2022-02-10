package com.phoenixhell.securityuaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author phoenixhell
 * @since 2021/10/11 0011-下午 3:13
 */
@Configuration
public class TokenConfig {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    //对称秘钥，资源服务器使用该秘钥来验证
   private static final String TOKEN_KEY="key";

    /**
     * token 存入redis
     */
    @Bean
    public TokenStore jwtTokenStore(){
       // return new InMemoryTokenStore();  //内存令牌存储策咯
        /*
        //使用JWT token 后  token自带内容  无需存储令牌相关的信息
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        redisTokenStore.setPrefix("AccessToken:");
        return redisTokenStore;*/

        //使用JWT token
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 配置密钥 (对称和非对称)
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        //对称秘钥，资源服务器使用该秘钥来验证(解密token)
        accessTokenConverter.setSigningKey(TOKEN_KEY);
        return accessTokenConverter;
    }
}
