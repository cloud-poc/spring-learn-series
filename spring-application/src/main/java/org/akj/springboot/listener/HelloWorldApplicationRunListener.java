package org.akj.springboot.listener;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.time.Duration;

@Slf4j
@NoArgsConstructor
public class HelloWorldApplicationRunListener implements SpringApplicationRunListener {

    public HelloWorldApplicationRunListener(SpringApplication application, String[] args){

    }

    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        log.info("HelloWorldApplicationRunListener published starting event.");
        SpringApplicationRunListener.super.starting(bootstrapContext);
    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        log.info("HelloWorldApplicationRunListener published environmentPrepared event.");
        SpringApplicationRunListener.super.environmentPrepared(bootstrapContext, environment);
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        log.info("HelloWorldApplicationRunListener published contextPrepared event.");
        SpringApplicationRunListener.super.contextPrepared(context);
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        log.info("HelloWorldApplicationRunListener published contextLoaded event.");
        SpringApplicationRunListener.super.contextLoaded(context);
    }

    @Override
    public void started(ConfigurableApplicationContext context, Duration timeTaken) {
        log.info("HelloWorldApplicationRunListener published started event.");
        SpringApplicationRunListener.super.started(context, timeTaken);
    }

    @Override
    public void ready(ConfigurableApplicationContext context, Duration timeTaken) {
        log.info("HelloWorldApplicationRunListener published ready event.");
        SpringApplicationRunListener.super.ready(context, timeTaken);
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        log.info("HelloWorldApplicationRunListener published failed event.");
        SpringApplicationRunListener.super.failed(context, exception);
    }
}
