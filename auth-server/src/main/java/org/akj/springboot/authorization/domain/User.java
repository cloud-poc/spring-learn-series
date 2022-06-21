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
public class Users extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(length = 32)
    private String id;

    @Column(unique = true, name = "user_id")
    private String userName;
    private String password;
    private String aliasName;
    private ProfileStatus status;
    private int failedLoginAttempts;
    private Instant lockedTime;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "user_to_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"
            ))
    private Set<Group> userGroups = new HashSet<>();

    @OneToOne
    private UserInfo userInfo;

}