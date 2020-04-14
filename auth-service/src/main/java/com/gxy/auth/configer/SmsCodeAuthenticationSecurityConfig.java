package com.gxy.auth.configer;

import com.gxy.auth.converter.SmsCodeAuthenticationProvider;
import com.gxy.auth.filter.SmsCodeAuthenticationFilter;
import com.gxy.auth.handler.MobileLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Qualifier("userDetailServiceImpl")
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private MobileLoginSuccessHandler mobileLoginSuccessHandler;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter("/authentication/mobile");
        smsCodeAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(mobileLoginSuccessHandler);
        SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        // 将SmsCodeAuthenticationFilter放到过滤器链的UsernamePasswordAuthenticationFilter的后面
        builder.authenticationProvider(provider)
                .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        //super.configure(builder);
    }
}
