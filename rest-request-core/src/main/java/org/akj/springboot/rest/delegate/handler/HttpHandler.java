package org.akj.springboot.rest.delegate.handler;

import org.akj.springboot.rest.delegate.bean.MethodMetaData;

public interface HttpHandler {
    Object invoke(MethodMetaData methodMetaData);
}
