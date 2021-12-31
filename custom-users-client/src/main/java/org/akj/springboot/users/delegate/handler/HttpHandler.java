package org.akj.springboot.users.delegate.handler;

import org.akj.springboot.users.delegate.bean.MethodMetaData;

public interface HttpHandler {
    Object invoke(MethodMetaData methodMetaData);
}
