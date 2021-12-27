package org.akj.springboot.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse {
    public int status;
    public String message;
}
