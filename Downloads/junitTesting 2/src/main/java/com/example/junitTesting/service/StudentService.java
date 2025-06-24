package com.example.junitTesting.service;

import com.example.junitTesting.entity.Student;

import java.util.List;

public interface StudentService {

    void saveStudent(Student student);
    void deleteStudent(Long id);
    Student findById(Long id);
    List<Student> getAllStudent();

}
