package org.akj.springboot.rest.delegate.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.Map;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MethodMetaData {
    private String url;

    private RequestMethod method;

    private Map<String, String> headers;

    private Map<String, String> params;

    private Map<String, String> pathVariables;

    protected Object body;

    private Method raw;

    private Class<?> returnType;

    private Class<?> actualReturnType;

    private boolean isReactive;
}
