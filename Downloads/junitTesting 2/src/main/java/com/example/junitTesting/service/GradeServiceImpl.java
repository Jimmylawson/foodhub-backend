package com.example.junitTesting.service;

import com.example.junitTesting.entity.Grade;
import com.example.junitTesting.entity.Student;
import com.example.junitTesting.repository.GradeRepository;
import com.example.junitTesting.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;


    @Override
    public void addGradeToStudent(Long studentId, Grade grade) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        grade.setStudent(student);
        gradeRepository.save(grade);

    }

    @Override
    public List<Grade> getGradesByStudentId(Long studentId) {
        return gradeRepository.findByStudentId(studentId);
    }

    @Override
    public void deleteGrade(Long gradeId) {
        gradeRepository.deleteById(gradeId);
    }
}
