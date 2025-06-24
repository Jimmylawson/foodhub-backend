package com.example.junitTesting.ttd;


import com.example.junitTesting.entity.Grade;
import com.example.junitTesting.entity.Student;
import com.example.junitTesting.repository.GradeRepository;
import com.example.junitTesting.repository.StudentRepository;
import com.example.junitTesting.service.StudentService;
import com.example.junitTesting.service.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void saveStudent_setsGradeStudentReferenceAndSaves(){
        var student =  Student.builder()
                .name("Aayush").email("ayush@gmail.com")
                .build();
        Grade grade = Grade.builder()
                .subject("Maths")
                .score(90)
                .build();
        student.setGrades(List.of(grade));

        studentService.saveStudent(student);

        assertEquals(student,grade.getStudent());
        verify(studentRepository).save(student);

    }

    @Test
    void findById_returnStudentIfExists(){
        var student =  Student.builder()
                .name("Aayush").email("ayush@gmail.com")
                .build();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Student result  = studentService.findById(1L);

        assertEquals("Aayush",result.getName());
    }


    @Test
    void deleteStudent_deletesStudent(){
        when(studentRepository.existsById(1L)).thenReturn(true);
        studentService.deleteStudent(1L);
        verify(studentRepository).deleteById(1L);
    }

}
