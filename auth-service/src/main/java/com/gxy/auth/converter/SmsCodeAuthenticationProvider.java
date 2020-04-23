package com.gxy.auth.converter;

import com.gxy.auth.exception.VerificationCodeMisMatchException;
import org.mockito.internal.matchers.InstanceOf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {
    @Qualifier("userDetailServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
        PrincipalMobile principal = (PrincipalMobile) authenticationToken.getPrincipal();
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getMobile());
        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        // TODO 在这里校验验证码是否正确，验证码一般存放到redis中
        String code ="2212";
        if(!code.equals(principal.getCode())){
            throw new VerificationCodeMisMatchException("验证码错误！");
        }

        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken( userDetails.getAuthorities(),userDetails);
        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return  SmsCodeAuthenticationToken.class.isAssignableFrom(aClass);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
