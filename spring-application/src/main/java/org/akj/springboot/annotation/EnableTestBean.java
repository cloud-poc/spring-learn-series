package org.akj.springboot.annotation;

import org.akj.springboot.config.AppConfig;
import org.akj.springboot.config.TestBeanImportBeanDefinitionRegistrar;
import org.akj.springboot.config.TestBeanImportSelector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(TestBeanImportSelector.class)
//@Import(AppConfig.class)
//@Import(TestBeanImportBeanDefinitionRegistrar.class)
public @interface EnableTestBean {
}
