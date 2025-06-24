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
public class StudentServiceImpl implements  StudentService {
    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;


    @Override
    public void saveStudent(Student student) {
        for (Grade grade : student.getGrades()) {
            grade.setStudent(student);
        }
        studentRepository.save(student);

    }

    @Override
    public void deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        }


    }

    @Override
    public Student findById(Long id) {

        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }
}
