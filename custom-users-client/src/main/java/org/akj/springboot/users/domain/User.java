package org.akj.springboot.users.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity  {

    private UserInfo userInfo;

    private String userName;

    private AuthType authType;

    private String password;

    private String token;

    private UserStatus userStatus;

}