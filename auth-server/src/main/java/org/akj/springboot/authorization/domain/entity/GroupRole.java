package org.akj.springboot.authorization.domain.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
public class GroupRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy="native")
    private Long id;

    @Column(nullable = false)
    private String groupId;

    @Column(nullable = false)
    private String roleId;

    private String remark;
}
