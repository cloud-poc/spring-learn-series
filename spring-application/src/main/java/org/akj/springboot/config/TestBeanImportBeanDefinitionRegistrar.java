package org.akj.springboot.config;

import org.akj.springboot.beans.TestBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class TestBeanImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        GenericBeanDefinition rootBeanDefinition = new GenericBeanDefinition();
        rootBeanDefinition.setBeanClassName(TestBean.class.getName());
        rootBeanDefinition.setBeanClass(TestBean.class);
        registry.registerBeanDefinition("testBean", rootBeanDefinition);
    }
}
