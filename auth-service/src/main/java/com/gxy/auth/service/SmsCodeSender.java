package com.gxy.auth.service;

public interface SmsCodeSender {
    void send(String mobile,String code);
}
