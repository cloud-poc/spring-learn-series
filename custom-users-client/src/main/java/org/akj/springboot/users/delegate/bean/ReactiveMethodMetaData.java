package org.akj.springboot.users.delegate.bean;

import lombok.Data;
import reactor.core.publisher.Mono;

@Data
public class ReactiveMethodMetaData extends MethodMetaData {
    private boolean isFlux;

    private Mono<?> reactiveBody;


}

