package org.akj.springboot.authorization.domain.entity;

import lombok.Data;
import lombok.ToString;
import org.akj.springboot.authorization.domain.AuthType;
import org.akj.springboot.authorization.domain.UserStatus;
import org.akj.springboot.common.domain.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "user")
@Data
@ToString(callSuper = true)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    private String aliasName;

    @Column(nullable = false)
    private AuthType authType;

    @NotNull
    @Pattern(regexp = "^(?:(?:\\+|00)86)?1(?:3\\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\\d|9\\d)\\d{8}$")
    private String phoneNumber;

    @Pattern(regexp = "^(.+)@ (.+)$")
    private String email;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private int failedLoginAttempts;
    private Instant lockedTime;
}