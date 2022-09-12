package org.akj.springboot.authorization.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserLoginRequest {
    @NotNull
    private String authType;

    @NotBlank
    @NotNull
    private String userName;

    private String password;

    private String code;
}
