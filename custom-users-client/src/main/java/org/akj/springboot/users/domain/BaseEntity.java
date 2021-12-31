package org.akj.springboot.users.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.Instant;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

    protected String id;

    protected Instant createDate;

    protected Instant lastModifiedDate;

    protected String createBy;

    protected String lastModifiedBy;
}
