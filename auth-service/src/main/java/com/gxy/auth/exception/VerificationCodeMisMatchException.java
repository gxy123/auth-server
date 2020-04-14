package com.gxy.auth.exception;

import org.springframework.security.authentication.AuthenticationServiceException;

public class VerificationCodeMisMatchException extends AuthenticationServiceException {
    public VerificationCodeMisMatchException(String msg) {
        super(msg);
    }
}
