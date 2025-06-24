package com.example.junitTesting.ttd;


import com.example.junitTesting.controller.studentController;
import com.example.junitTesting.entity.Student;
import com.example.junitTesting.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(studentController.class)
@Import(StudentControllerTest.TestConfig.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;


    @Test
    void testAddStudent_returnsCreated() throws Exception {
        Student student = Student
                .builder()
                        .name("Aayush")
                        .email("ayush@gmail.com")
                        .build();;
        student.setId(1L);

        doNothing().when(studentService).saveStudent(any(Student.class));

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Aayush\",\"email\":\"ayush@gmail.com\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void getStudent() throws Exception {
        var student = Student.builder()
                        .name("Aayush")
                                .email("ayush@gmail.com")
                                .build();

        when(studentService.findById(1L)).thenReturn(student);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Aayush"))
                .andExpect(jsonPath("$.email").value("ayush@gmail.com"));

    }

    @Test
    void deleteStudent() throws Exception {

        doNothing().when(studentService).deleteStudent(1L);

        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isNoContent())
                .andReturn();

    }

    /// This say inside the test class
    @TestConfiguration
    static class TestConfig {
        @Bean
        public StudentService studentService() {
            return Mockito.mock(StudentService.class);
        }
    }
}



