package org.akj.springboot;

import org.akj.springboot.annotation.AutoIgnore;
import org.akj.springboot.annotation.EnableTestBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.Set;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {CustomBeanFilter.class}))
//@ComponentScan(excludeFilters = @ComponentScan.Filter(type=FilterType.REGEX, pattern = "org.akj.controller.Hello*"))
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = AutoIgnore.class))
//@Import(AppConfig.class)
//@Import(TestBeanImportSelector.class)
@EnableTestBean
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
//        SpringApplication application = new SpringApplication(Application.class);
//        application.setHeadless(false);
//        application.setWebApplicationType(WebApplicationType.NONE);
//        application.setMainApplicationClass(Application.class);
//        application.run(args);

//        new SpringApplicationBuilder(Application.class)
//                .bannerMode(Banner.Mode.CONSOLE)
//                .main(Application.class)
//                .profiles("test")
//                .run(args);

//        SpringApplication application = new SpringApplication();
//        application.setHeadless(false);
//        application.setWebApplicationType(WebApplicationType.SERVLET);
//        application.setMainApplicationClass(Application.class);
//        application.setSources(Set.of(Application.class.getName()));
//        application.run(args);

    }

}
