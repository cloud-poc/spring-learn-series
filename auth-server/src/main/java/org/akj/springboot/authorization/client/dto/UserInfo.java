package org.akj.springboot.authorization.client.dto;

import lombok.Data;
import lombok.ToString;
import org.akj.springboot.authorization.domain.Gender;
import org.akj.springboot.common.domain.BaseEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserInfo {

    private String id;

    private String firstName;

    private String middleName;

    private String lastName;

    private int age;

    private Gender gender;

    private String phone;

    private String telPhone;

    private String email;
}
