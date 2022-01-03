package org.akj.springboot.rest.delegate;

public interface DelegateProxy {
    Object newInstance(Class<?> target);
}
