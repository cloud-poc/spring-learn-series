package org.akj.springboot.config;

import org.akj.springboot.annotation.AutoIgnore;
import org.akj.springboot.beans.TestBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(TestBean.class)
@AutoIgnore
public class AppConfig {
}
