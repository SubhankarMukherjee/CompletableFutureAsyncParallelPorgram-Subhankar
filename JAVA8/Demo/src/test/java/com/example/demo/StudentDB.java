package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class StudentDB {

    public static List<Student> getStudents()
    {
        return Arrays.asList(
                new Student("Sam", Arrays.asList("painting","basketball"),20,78.3,11),
                new Student("Bam", Arrays.asList("swimming","basketball"),21,80.3,21),
                new Student("jam", Arrays.asList("football","cricket"),18,72.3,10),
                new Student("Tam", Arrays.asList("painting","basketball"),16,75.2,9),
                new Student("Nam", Arrays.asList("swimming","basketball"),15,98.3,5),
                new Student("Aam", Arrays.asList("painting","swimming"),18,78.4,13)

        );
    }
    public static Supplier<Student> studentSupplier =()->
    {
        Bike bike= new Bike();
        bike.setModel("Caliber 123");
        bike.setName("Bajaj");
        Student student = new Student("Sam", Arrays.asList("painting", "basketball"), 20, 78.3, 11);
        student.setBikes(Optional.ofNullable(bike));
        return student;
    };
}
