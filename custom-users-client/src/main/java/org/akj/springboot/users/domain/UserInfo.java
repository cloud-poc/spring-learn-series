package org.akj.springboot.users.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {
    private String firstName;

    private String middleName;

    private String lastName;

    private int age;

    private Gender gender;

    private String phone;

    private String telPhone;
}
