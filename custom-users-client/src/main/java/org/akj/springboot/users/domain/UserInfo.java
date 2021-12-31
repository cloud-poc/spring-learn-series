package org.akj.springboot.users.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserInfo implements Serializable {
    private String firstName;

    private String middleName;

    private String lastName;

    private int age;

    private Gender gender;

    private String phone;

    private String telPhone;
}
