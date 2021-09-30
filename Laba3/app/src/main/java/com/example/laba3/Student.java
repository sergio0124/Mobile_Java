package com.example.laba3;

public class Student {
    public String Name;
    public int Age;
    public boolean IsStuding;

    public Student(String name, int age, boolean isStuding) {
        Name=name;
        Age = age;
        IsStuding = isStuding;
    }

    @Override
    public String toString() {
        return "Student{" +
                "Name='" + Name + '\'' +
                ", Age=" + Age +
                ", IsStuding=" + IsStuding +
                '}';
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isStuding() {
        return IsStuding;
    }

    public void setStuding(boolean studing) {
        IsStuding = studing;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getName() {
        return Name;
    }
}
