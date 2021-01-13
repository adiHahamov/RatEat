package com.adiandnoy.RatEat.model;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public final static Model instance = new Model();

    private Model(){
        for(int i=0;i<100;i++) {
            Student student = new Student();
            student.id = "" + i;
            student.name = "Moshe " + i;
            data.add(student);
        }
    }

    List<Student> data = new LinkedList<Student>();

    public List<Student> getAllStudents() {
        return data;
    }
}
