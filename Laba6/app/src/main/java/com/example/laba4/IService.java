package com.example.laba4;

import java.util.List;

public interface IService {

    void insert(Student stud);

    void update(Student model);

    List<Student> getList();

    void delete(Student student);

    int getCount();

}
