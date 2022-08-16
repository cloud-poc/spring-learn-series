package org.akj.springboot.authorization.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class AddUserRequest {
    @NotNull
    @Length(min = 6, max = 32)
    private String userName;
    @NotNull
    @Length(min = 8, max = 32)
    private String password;
    private String aliasName;
    @NotNull
    private Integer userRefId;
}
