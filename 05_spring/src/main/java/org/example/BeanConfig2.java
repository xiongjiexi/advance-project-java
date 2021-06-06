package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class BeanConfig2 {
    @Bean
    public Student student() {
        Student s = new Student();
        s.setId(999);
        s.setName("@beanConfig jesse999");
        return s;
    }

    @Bean
    public Klass klass(Student student) {
        Klass klass = new Klass();
        klass.setStudents(Collections.singletonList(student));
        return klass;
    }

    @Bean
    public School school(Klass klass, Student student) {
        School school = new School();
        school.setKlass(klass);
        school.setStudent(student);
        return school;
    }
}
