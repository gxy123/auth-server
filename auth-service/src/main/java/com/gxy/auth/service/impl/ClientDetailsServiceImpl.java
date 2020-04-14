package com.gxy.auth.service.impl;

import com.gxy.auth.client.domain.OauthCliendetailsDO;
import com.gxy.auth.client.query.OauthCliendetailsQueryDO;
import com.gxy.auth.service.OauthCliendetailsService;
import com.gxy.client.base.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {
    @Autowired
    private OauthCliendetailsService oauthCliendetailsService;
    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        OauthCliendetailsQueryDO queryDO =new OauthCliendetailsQueryDO();
        queryDO.setClientId(s);
        CommonResult<OauthCliendetailsDO> oauthCliendetailsDOCommonResult = oauthCliendetailsService.queryFirst(queryDO);
        OauthCliendetailsDO result = oauthCliendetailsDOCommonResult.getResult();
        BaseClientDetails baseClientDetails =new BaseClientDetails();
        baseClientDetails.setClientSecret(result.getClientSecret());
        baseClientDetails.setClientId(result.getClientId());
        List<String> authorizedGrantTypes =new ArrayList<>();
        baseClientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
        baseClientDetails.setResourceIds(Arrays.asList(result.getResourceIds()));
        return baseClientDetails;
    }
}
