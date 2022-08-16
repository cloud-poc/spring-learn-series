package org.akj.springboot.authorization.domain;

import lombok.Data;
import lombok.ToString;
import org.akj.springboot.common.domain.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "user_profile")
@Data
@ToString(callSuper = true)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy="native")
    private Integer id;

    @Column(unique = true, name = "user_id")
    private String userName;
    private String password;
    private String aliasName;
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;
    private int failedLoginAttempts;
    private Instant lockedTime;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    },fetch = FetchType.EAGER)
    @JoinTable(name = "user_authorities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "id"
            ))
    private Set<Authority> userAuthorities = new HashSet<>();

    private String userRefId;

}