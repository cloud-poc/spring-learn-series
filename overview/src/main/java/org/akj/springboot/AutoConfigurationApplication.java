package org.akj.springboot;

import org.akj.springboot.annotation.AutoIgnore;
import org.akj.springboot.annotation.EnableTestBean;
import org.akj.springboot.beans.TestBean;
import org.akj.springboot.config.AppConfig;
import org.akj.springboot.config.TestBeanImportSelector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {CustomBeanFilter.class}))
@ComponentScan(excludeFilters = @ComponentScan.Filter(type=FilterType.REGEX, pattern = "org.akj.controller.Hello*"))
@ComponentScan(excludeFilters = @ComponentScan.Filter(type=FilterType.ANNOTATION, classes = AutoIgnore.class))
//@Import(AppConfig.class)
//@Import(TestBeanImportSelector.class)
@EnableTestBean
public class AutoConfigurationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoConfigurationApplication.class, args);
    }

}
