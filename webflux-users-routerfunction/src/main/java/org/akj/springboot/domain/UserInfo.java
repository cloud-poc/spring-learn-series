package org.akj.springboot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {
    @NotEmpty
    private String firstName;

    private String middleName;

    @NotEmpty
    private String lastName;

    @Range(min = 0, max = 200)
    private int age;

    private Gender gender;

    @NotNull
    private String phone;

    private String telPhone;

}
