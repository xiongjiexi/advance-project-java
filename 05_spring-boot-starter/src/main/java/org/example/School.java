package org.example;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Data
@Component
public class School implements ISchool {
    @Autowired
    private Klass klass;

    @Autowired
    private Student student;

    @Autowired
    private SchoolProperties prop;

    private String description;

    public void ding() {
        student.setName(prop.getName());
        klass.setStudents(Collections.singletonList(student));
        this.description = prop.getDescription();
        System.out.println("Class1 have " + this.klass.getStudents().size() + " students and one is " + this.student);
    }
}
