package org.example;

import lombok.Data;

@Data
public class School implements ISchool {
//    @Autowired(required = true) //primary
    private Klass klass;

    private Student student;

    public void ding() {
        System.out.println("Class1 have " + this.klass.getStudents().size() + " students and one is " + this.student);
    }
}
