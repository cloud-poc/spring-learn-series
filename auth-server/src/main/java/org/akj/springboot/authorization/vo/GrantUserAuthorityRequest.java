package org.akj.springboot.authorization.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class GrantUserAuthorityRequest {
    @Size(min = 1)
    @NotNull
    private List<String> authorities;
}
