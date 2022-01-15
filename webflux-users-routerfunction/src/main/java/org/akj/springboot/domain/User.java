package org.akj.springboot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Document(collection =  "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity  {

    @Valid
    private UserInfo userInfo;

    @NotEmpty
    @Indexed(unique = true)
    private String userName;

    @NotNull
    private AuthType authType;

    private String password;

    private String token;

    private UserStatus userStatus;

    @JsonIgnore
    private String schemaVersion;

}