package org.akj.springboot.users;

import org.akj.springboot.rest.delegate.annotation.EnableWebClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.akj.springboot")
@EnableWebClients
public class WebfluxUsersClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxUsersClientApplication.class, args);
    }

}
