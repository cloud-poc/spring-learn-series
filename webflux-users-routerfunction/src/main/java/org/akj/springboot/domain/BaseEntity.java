package org.akj.springboot.domain;

import lombok.Data;
import org.springframework.data.annotation.*;

import java.io.Serializable;
import java.time.Instant;

@Data
public class BaseEntity implements Serializable {

    @Id
    protected String id;

    @CreatedDate
    protected Instant createDate;

    @LastModifiedDate
    protected Instant lastModifiedDate;

    @CreatedBy
    protected String createBy;

    @LastModifiedBy
    protected String lastModifiedBy;
}
