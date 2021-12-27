package org.akj.springboot.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DeduceMainApplicationClassTest {

    @Test
    @SneakyThrows
    public void test(){
        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            if ("main".equals(stackTraceElement.getMethodName())) {
//                return Class.forName(stackTraceElement.getClassName());
                log.info(stackTraceElement.getClassName());
            }
        }
    }
}
