package org.akj.springboot.users.delegate;

public interface DelegateProxy {
    Object newInstance(Class<?> target);
}
