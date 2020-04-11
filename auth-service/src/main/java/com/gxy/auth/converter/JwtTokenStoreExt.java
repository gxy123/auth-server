package com.gxy.auth.converter;

import com.gxy.service.base.BaseServiceAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

public class JwtTokenStoreExt extends JwtTokenStore {
    private static final Logger log = LoggerFactory.getLogger(JwtTokenStoreExt.class);
    public JwtTokenStoreExt(JwtAccessTokenConverter jwtTokenEnhancer) {
        super(jwtTokenEnhancer);
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        log.info("JwtTokenStoreExt_set_redis,",token.getAdditionalInformation().get("jti"));

        //保存redis标识，代表有效，删除代表无效
        //保存userid-jti-value
        super.storeAccessToken(token, authentication);
    }
}
