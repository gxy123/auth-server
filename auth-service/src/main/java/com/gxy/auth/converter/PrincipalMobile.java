package com.gxy.auth.converter;

public class PrincipalMobile {
    private final String mobile;
    private final String code;

    public PrincipalMobile( String mobile, String code) {
        this.mobile = mobile;
        this.code = code;
    }


    public String getMobile() {
        return mobile;
    }

    public String getCode() {
        return code;
    }


}