package org.akj.springboot.users.delegate.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebClient {
    String url() default "";

}
