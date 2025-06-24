package com.example.junitTesting.service;


import com.example.junitTesting.entity.Grade;

import java.util.List;

public interface GradeService {
    void addGradeToStudent(Long studentId, Grade grade);
    List<Grade> getGradesByStudentId(Long studentId);

    void deleteGrade(Long gradeId);
}