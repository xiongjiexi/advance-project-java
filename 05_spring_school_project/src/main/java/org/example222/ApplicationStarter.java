package org.example222;

import org.example.School;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
//@EnableSchool
public class ApplicationStarter {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(ApplicationStarter.class, args);
        School school = (School) ctx.getBean("school");
        school.ding();
    }
}
