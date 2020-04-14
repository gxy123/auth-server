package com.gxy.auth.filter;

import com.gxy.auth.converter.PrincipalMobile;
import com.gxy.auth.converter.SmsCodeAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public SmsCodeAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if (!httpServletRequest.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: "+httpServletRequest.getMethod());
        }
        String mobile = httpServletRequest.getHeader("mobile");
        String code = httpServletRequest.getHeader("smsCode");
        PrincipalMobile p =new PrincipalMobile(mobile,code);
        // 封装令牌
        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(p);
        setDetails(httpServletRequest, authRequest);
        // 开始认证
        return this.getAuthenticationManager().authenticate(authRequest);
    }
    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

}
