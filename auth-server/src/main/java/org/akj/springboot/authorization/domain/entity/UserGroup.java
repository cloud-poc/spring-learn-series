package org.akj.springboot.authorization.domain.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy="native")
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String groupId;

    private String remark;
}
