package org.example;

import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class Student {
    private int id;

    private String name;

    private String beanName;

    private ApplicationContext applicationContext;

    public void init(){
        System.out.println("hello...........");
    }

    public void print() {
        System.out.println(this.beanName);
        System.out.println("   context.getBeanDefinitionNames() ===>> "
                + String.join(",", applicationContext.getBeanDefinitionNames()));
    }
}
