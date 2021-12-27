package org.akj.springboot.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface AutoIgnore {
    @AliasFor(value = "name")
    String value() default "";

    @AliasFor(value = "value")
    String name() default "";
}
