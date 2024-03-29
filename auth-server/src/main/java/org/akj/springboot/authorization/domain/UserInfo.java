package org.akj.springboot.authorization.domain;

import lombok.Data;
import lombok.ToString;
import org.akj.springboot.common.domain.BaseEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "user_info")
@ToString(callSuper = true)
public class UserInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy="native")
    private Integer id;

    @NotEmpty
    private String firstName;

    private String middleName;

    @NotEmpty
    private String lastName;

    @Range(min = 0, max = 200)
    private int age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    private String phone;

    private String telPhone;

    private String email;
}
