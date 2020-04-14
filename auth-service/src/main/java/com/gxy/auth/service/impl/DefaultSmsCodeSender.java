package com.gxy.auth.service.impl;

import com.gxy.auth.service.SmsCodeSender;

public class DefaultSmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String mobile, String code) {

        System.out.println("向手机发送短信验证码"+mobile+"---"+code);
    }
}
