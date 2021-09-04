package com.jongtk.cloudmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CloudMapApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudMapApplication.class, args);
    }

}
