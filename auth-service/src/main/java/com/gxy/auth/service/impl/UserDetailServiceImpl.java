package com.gxy.auth.service.impl;

import com.gxy.auth.service.UserService;
import com.gxy.client.base.CommonResult;
import com.gxy.user.client.domain.SyUserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userDetailsService;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        CommonResult<SyUserDO> userByUserName = userDetailsService.getUserByUserName(s);
        SyUserDO result = userByUserName.getResult();
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ADMIN";
            }
        });
        User user =new User(result.getUsername(),result.getPassword(),authorities);
        return user;
    }
}
