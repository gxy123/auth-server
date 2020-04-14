package com.gxy.auth.configer;


import com.gxy.auth.converter.JwtTokenStoreExt;
import com.gxy.auth.converter.MyJwtAccessTokenConverter;
import com.gxy.auth.service.SmsCodeSender;
import com.gxy.auth.service.impl.DefaultSmsCodeSender;
import com.gxy.auth.utils.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

/**
 * 授权服务配置
 */
@RefreshScope
@Configuration
@EnableAuthorizationServer//说明这是一个授权服务类
public class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    private static final String SOURCE_ID = "order";
    private static final int ACCESS_TOKEN_TIMER = 60 * 60 * 24;
    private static final int REFRESH_TOKEN_TIMER = 60 * 60 * 24 * 30;

    @Value("${fyk.authorization.token-store}")
    private String JWT_SY_STORE;

    /* @Autowired
     private UserDetailsService userDetailsService;
     */
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;//认证管理器
    @Autowired
    private DataSource dataSource;
    @Autowired
    private PasswordEncoder passwordEncoder;//密码加密类

    /* 下面是一些默认的端点 URL：
     /oauth/authorize：授权端点
     /oauth/token：令牌端点
     /oauth/confirm_access：用户确认授权提交端点
     /oauth/error：授权服务错误信息端点
     /oauth/check_token：用于资源服务访问的令牌解析端点
     /oauth/token_key：提供公有密匙的端点，如果你使用JWT令牌的话
     授权端点的 URL 应该被 Spring Security 保护起来只供授权用户访问
*/
    //授权端点开放
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                // 设置一个编码方式
                //注释都写的很清楚了。需要说明的一点的，这里配置了一个密码的编码方式，
                // 这个密码就是OauthClient类中的clientSecret字段
                .passwordEncoder(passwordEncoder)

                .tokenKeyAccess("permitAll()")  //允许所有资源服务器访问公钥端点（/oauth/token_key）

                .checkTokenAccess("isAuthenticated()")//只允许验证用户访问令牌解析端点（/oauth/check_token）
                // 允许客户端发送表单来进行权限认证来获取令牌
                //主要是让/oauth/token支持client_id以及client_secret作登录认证
                .allowFormAuthenticationForClients();
    }

    //客户端的配置信息既可以放在内存中，也可以
    // 放在数据库中，也可以是直接搞的一些策略。
    // 这里我使用的是设置clients.withClientDetails(clientDetailsService);
    // 这里就是使用自己搞的一些策略。也就是客户端在获取token的时候，
    // 会给你个clientId，然后根据这个clientId返回ClientDetails就可以，
    // 至于是怎么得到的ClientDetails，那就自己搞了。这里，我还是使用的是到数据库中查找：
    // （个人觉得这种方式很好，如果哪天不想用数据查了，用内存，用配置文件等等，
    // 也就在这里改了就是，隐藏了数据获取的细节）
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        Collections.synchronizedMap(new HashMap());
        //数据库的方式
        //clients.jdbc().passwordEncoder(passwordEncoder);
        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
        //内存中的方式
       /* clients.inMemory()
                .withClient("myapp")
                .resourceIds("resourceId")
                .authorizedGrantTypes("password", "refresh_token", "authorization_code")//客户端有那些授权方式权限
                .scopes("all").authorities("ADMIN")
                .secret(passwordEncoder.encode("lxapp"))
                .redirectUris("http://www.baidu.com")
                .accessTokenValiditySeconds(ACCESS_TOKEN_TIMER)
                .refreshTokenValiditySeconds(REFRESH_TOKEN_TIMER)
                .autoApprove(true);// //登录后绕过批准询问(/oauth/confirm_access)*/
        //password=
        //authorization_code=授权码方式
        //

    }

    public static void main(String[] args) {
        System.out.printf(new BCryptPasswordEncoder().encode("123456"));
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(getTokenStore())
                .authenticationManager(authenticationManager)
                // .userDetailsService(userDetailsService)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        //如果生成token方式是jwt的话就走下面逻辑
        //if (JWT_SY_STORE.equalsIgnoreCase("jwt_sy")) {
            // token生成方式
            endpoints.tokenEnhancer(accessTokenConverter());

       // }
    }

    @Bean
    public TokenStore getTokenStore() {
        if (JWT_SY_STORE.equalsIgnoreCase("jwt_sy")) {// jwt对称加密存储方式
            return new JwtTokenStoreExt(accessTokenConverter());
        } else {
            JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(dataSource);
            jdbcTokenStore.setAuthenticationKeyGenerator(authentication -> "FYK" + UUID.randomUUID().toString().replace("-", ""));
            return jdbcTokenStore;
        }

    }

    @Bean
    protected JwtAccessTokenConverter accessTokenConverter() {
        MyJwtAccessTokenConverter converter = new MyJwtAccessTokenConverter();
        // 设置设置JWT签名密钥对称密钥方式
        //converter.setSigningKey("fyk123");
        //***非对称方式
        //使用私钥编码 JWT 中的  OAuth2 令牌
        converter.setKeyPair(RSAUtil.GetKeyPair());
        return converter;
    }
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)//当容器中找到了SmsCodeSender的实现就不会再用此实现bean
    public SmsCodeSender smsCodeSender() { //方法的名字就是spring容器中bean的名字
        return new DefaultSmsCodeSender();
    }


}
