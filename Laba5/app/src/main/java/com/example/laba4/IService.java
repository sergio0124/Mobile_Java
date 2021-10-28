package com.example.laba4;

import java.util.List;

public interface IService
{

    public void insert(Student stud);

    public void update(Student model);

    public List<Student> getList();

    public void delete(Student student);

}
