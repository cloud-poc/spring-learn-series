package org.akj.springboot.authorization.vo;

import lombok.Data;
import org.akj.springboot.authorization.domain.AuthType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class UserRegistrationResponse {
    private Long id;

    private String userName;

    private String password;

    private String aliasName;

    private String phoneNumber;

    private String email;

    private AuthType authType;
}
