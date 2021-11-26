package com.example.laba4;

public class Student {
    public int id;
    public String name;
    public int age;
    public boolean isStuding;

    @Override
    public String toString() {
        return "Student{" +
                "Name='" + name + '\'' +
                ", Age=" + age +
                ", IsStuding=" + isStuding +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStuding() {
        return isStuding;
    }

    public void setStuding(boolean studing) {
        this.isStuding = studing;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }
}
