package org.akj.springboot.rest.delegate.annotation;

import org.akj.springboot.rest.delegate.WebClientsRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(WebClientsRegistrar.class)
public @interface EnableWebClients {
}
