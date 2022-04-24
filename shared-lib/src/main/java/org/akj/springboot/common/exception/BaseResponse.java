package org.akj.springboot.common.exception;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class BaseResponse {
	private String code ;
	private String message;
	private Object body;
}
