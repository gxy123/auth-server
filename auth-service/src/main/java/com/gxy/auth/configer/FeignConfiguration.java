package com.gxy.auth.configer;


import com.gxy.auth.interceptor.FeignInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @author: guoxiaoyu
 * @date : 2018/8/25
 */
@Configuration
public class FeignConfiguration {
   @Bean
    public FeignInterceptor getFeignInterceptor() {
        return new FeignInterceptor();
    }


}
