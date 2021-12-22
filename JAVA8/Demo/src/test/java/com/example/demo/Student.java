package com.example.demo;

import java.util.List;
import java.util.Optional;

public class Student {

    private String name;
    private List<String> activities;
    private int age;
    private double grade;
    private int notebooks;
    private Optional<Bike> bikes= Optional.empty();
    public Student (String name)
    {
        this.name=name;
    }
    public Student(String name, List<String> activities, int age, double grade,int notebooks ) {
        this.name = name;
        this.activities = activities;
        this.age = age;
        this.grade = grade;
        this.notebooks=notebooks;
    }

    public Student() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getActivities() {
        return activities;
    }

    public void setActivities(List<String> activities) {
        this.activities = activities;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }


    public int getNotebooks() {
        return notebooks;
    }

    public void setNotebooks(int notebooks) {
        this.notebooks = notebooks;
    }

    public Optional<Bike> getBikes() {
        return bikes;
    }

    public void setBikes(Optional<Bike> bikes) {
        this.bikes = bikes;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", activities=" + activities +
                ", age=" + age +
                ", grade=" + grade +
                ", notebooks=" + notebooks +
                ", bikes=" + bikes +
                '}';
    }
}
