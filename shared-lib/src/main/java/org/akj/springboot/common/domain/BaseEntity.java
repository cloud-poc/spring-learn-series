package org.akj.springboot.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

    @CreatedDate
    @JsonIgnore
    protected Instant createDate;

    @LastModifiedDate
    @JsonIgnore
    protected Instant lastModifiedDate;

    @CreatedBy
    @JsonIgnore
    protected String createBy;

    @LastModifiedBy
    @JsonIgnore
    protected String lastModifiedBy;
}
