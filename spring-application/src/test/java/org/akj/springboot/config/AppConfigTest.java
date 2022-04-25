package org.akj.springboot.config;

import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.beans.TestBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class AppConfigTest {

    @Autowired
    private TestBean bean;

    @Test
    public void test(){
        Assertions.assertEquals("test bean", bean.toString());
    }

}