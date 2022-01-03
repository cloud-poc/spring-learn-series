package org.akj.springboot.rest.delegate.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReactiveWebClient {
    String url() default "";
}
