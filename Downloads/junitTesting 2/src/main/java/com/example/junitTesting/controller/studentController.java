package com.example.junitTesting.controller;


import com.example.junitTesting.entity.Student;
import com.example.junitTesting.service.StudentService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class studentController {
    private final StudentService studentService;


    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        studentService.saveStudent(student);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudent(){

        return ResponseEntity.status(HttpStatus.OK).body(studentService.getAllStudent());
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        var student = studentService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(student);

    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

}
