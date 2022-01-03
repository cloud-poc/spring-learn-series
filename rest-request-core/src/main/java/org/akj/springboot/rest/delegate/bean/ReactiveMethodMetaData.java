package org.akj.springboot.rest.delegate.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import reactor.core.publisher.Mono;

@Data
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class ReactiveMethodMetaData extends MethodMetaData {
    private boolean isFlux;

    private Mono reactiveBody;

    private Class reactiveBodyType;

}

