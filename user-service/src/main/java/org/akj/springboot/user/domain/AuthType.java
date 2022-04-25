package org.akj.springboot.user.domain;

public enum AuthType {
    PASSWORD("PASSWORD"), OTP("OTP"), GITHUB("GITHUB"), WECHAT("WECHAT"), FACEBOOK("FACEBOOK");

    private String authType;

    AuthType(String authType) {
        this.authType = authType;
    }
}
