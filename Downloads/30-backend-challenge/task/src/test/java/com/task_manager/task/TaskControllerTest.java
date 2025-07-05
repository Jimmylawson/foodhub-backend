package com.task_manager.task;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.task_manager.task.controller.TaskController;
import com.task_manager.task.dto.RequestDto;
import com.task_manager.task.dto.ResponseDto;
import com.task_manager.task.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@Import(TaskControllerTest.TestConfig.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskService taskService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllTask() throws Exception {
        /// Given
        var tasks = List.of(
                ResponseDto.builder()
                        .username("user1")
                        .title("Task 1")
                        .description("Description 1")
                        .isCompleted(false)
                        .build(),
                ResponseDto.builder()
                        .username("user2")
                        .title("Task 2")
                        .description("Description 2")
                        .isCompleted(true)
                        .build()
        );
        /// THen
        when(taskService.getAllTasks()).thenReturn(tasks);


        /// When & Then
        mockMvc.perform(get("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[0].description").value("Description 1"))
                .andExpect(jsonPath("$[0].completed").value(false))   // ← here
                .andExpect(jsonPath("$[1].username").value("user2"))
                .andExpect(jsonPath("$[1].title").value("Task 2"))
                .andExpect(jsonPath("$[1].description").value("Description 2"))
                .andExpect(jsonPath("$[1].completed").value(true));  // ← and here


    }

    @Test
    public void createTask() throws Exception {
        /// Given
        var requestdto = new RequestDto("user1", "Test Task", "Description", false);

        /// When & Then
        mockMvc.perform(post("/api/v1/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestdto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Task created successfully"));


        verify(taskService).createTask(any(RequestDto.class));

    }


    @Test
    public void deleteTask_Test() throws Exception {
        /// Given
        Long taskID = 1L;

        mockMvc.perform(delete("/api/v1/task/{id}", taskID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(taskService).deleteTask(taskID);


    }

    @Test
    public void getTaskById() throws Exception {
        /// Given
        Long taskId = 1L;
        var task = ResponseDto.builder()
                .username("user1")
                .title("Task 1")
                .description("Description 1")
                .isCompleted(false)
                .build();
        when(taskService.getTaskById(taskId)).thenReturn(task);

        /// When/Then
        mockMvc.perform(get("/api/v1/task/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("user1"))
                .andExpect(jsonPath("$.title").value("Task 1"))
                .andExpect(jsonPath("$.description").value("Description 1"))
                .andExpect(jsonPath("$.completed").value(false));

        verify(taskService).getTaskById(taskId);

    }

    @Test
    public void updateTask() throws Exception {
        /// Given
        Long taskId = 1L;
        var requestDto = new RequestDto("user1", "Task 1", "Description 1", false);
//        var task = ResponseDto.builder()
//                .username("user1")
//                .title("Task 1")
//                .description("Description 1")
//                .isCompleted(false)
//                .build();
        doNothing().when(taskService).updateTask(taskId, requestDto);

        /// When/Then
        mockMvc.perform(put("/api/v1/task/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Task updated successfully"));

        verify(taskService).updateTask(eq(taskId), any(RequestDto.class));
    }

    @TestConfiguration
    static class TestConfig{
        @Bean
        public TaskService taskService() {
            return Mockito.mock(TaskService.class);
        }

    }


}

