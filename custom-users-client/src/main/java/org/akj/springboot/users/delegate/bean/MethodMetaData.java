package org.akj.springboot.users.delegate.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MethodMetaData {
    private String url;

    private RequestMethod method;

    private Map<String, String> headers;

    private Map<String, Object> params;

    protected Object body;

    private Method raw;

    private Class<?> returnType;

    private Class<?> actualReturnType;
}
