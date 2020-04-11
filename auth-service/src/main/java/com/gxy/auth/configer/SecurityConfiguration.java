package com.gxy.auth.configer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.*;

//之所以要配置security，主要是因为在这个授权服务中，
// 还是有一些资源需要保护（所以，严格说来，它也是一个资源服务）。
// 比如：获取当前登录人的信息API（这个之后再说）。
@Configuration
@EnableWebSecurity        //开启web保护功能
@EnableGlobalMethodSecurity(prePostEnabled = true)    //开启在方法上的保护功能
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
   /* @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
        super.configure(auth);
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
// 这里是添加两个用户到内存中去，实际中是从#下面去通过数据库判断用户是否存在
      /*  PasswordEncoder passwordEncode = PasswordEncoderBean();
        String pwd = passwordEncode.encode("123456");
        ArrayList<UserDetails> userDetails = new ArrayList<>();
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ADMIN";
            }
        });
        User user_1 = new User("user_1", pwd, authorities);
        userDetails.add(user_1);
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(userDetails);*/
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        PasswordEncoder passwordEncode = PasswordEncoderBean();
        String pwd = passwordEncode.encode("123456");
        manager.createUser(User.withUsername("user_1").password(pwd).authorities("ADMIN").build());
        manager.createUser(User.withUsername("user_2").password(pwd).authorities("ADMIN").build());
        return manager;
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder PasswordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       // http.authorizeRequests().antMatchers("/oauth/**").permitAll().and().csrf().disable().authorizeRequests().anyRequest().authenticated();
            http.authorizeRequests().anyRequest().permitAll().and().httpBasic().and().csrf().disable();

    }
}
