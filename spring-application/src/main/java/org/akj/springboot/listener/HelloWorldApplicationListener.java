package org.akj.springboot.listener;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

@Slf4j
@NoArgsConstructor
public class HelloWorldApplicationListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationStartingEvent){
            log.info("HelloWorldApplicationListener received ApplicationStartingEvent");
        }else if(event instanceof ApplicationReadyEvent){
            log.info("HelloWorldApplicationListener received ApplicationReadyEvent");
        }
    }


}
