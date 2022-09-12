package org.akj.springboot.authorization.domain.entity;

import lombok.Data;
import org.akj.springboot.common.domain.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@Data
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy="native")
    private Integer roleId;

    private String roleName;

    private String description;

    private Set<Group> assignedGroups = new HashSet<>();

}
