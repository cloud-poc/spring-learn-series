package org.akj.springboot.authorization.vo;

import lombok.Data;
import org.akj.springboot.authorization.domain.AuthType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class UserRegistrationRequest {
    @NotNull
    @Length(min = 6, max = 32)
    private String userName;
    @NotNull
    @Length(min = 8, max = 32)
    private String password;

    private String aliasName;

    private String phoneNumber;

    private String email;

    private AuthType authType = AuthType.PASSWORD;
}
