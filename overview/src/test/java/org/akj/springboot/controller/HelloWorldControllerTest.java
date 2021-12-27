package org.akj.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.annotation.AutoIgnore;
import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.AnnotationUtils;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class HelloWorldControllerTest {

    @Test
    public void test() {
        AutoIgnore annotation = HelloWorldController.class.getAnnotation(AutoIgnore.class);
        log.info("name: {}, value: {}", annotation.name(), annotation.value());
    }

    @Test
    public void test1(){
        AutoIgnore annotation = AnnotationUtils.findAnnotation(HelloWorldController.class, AutoIgnore.class);
        log.info("name: {}, value: {}", annotation.name(), annotation.value());
    }

}