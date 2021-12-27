package org.akj.springboot.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HelloWorldApplicationContextInitializer<C extends ConfigurableApplicationContext> implements ApplicationContextInitializer<C> {
    @Override
    public void initialize(C applicationContext) {
        log.info("ConfigurableApplicationContext, type: {}, id:{}", applicationContext.getClass().getName(), applicationContext.getId());
        log.info("ConfigurableApplicationContext, beanFactory: {}", applicationContext.getBeanFactory());
    }
}
