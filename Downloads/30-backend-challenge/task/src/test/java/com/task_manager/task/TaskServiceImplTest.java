package com.task_manager.task;


import com.task_manager.task.dto.RequestDto;
import com.task_manager.task.dto.ResponseDto;
import com.task_manager.task.entity.Task;
import com.task_manager.task.exceptions.DuplicateTaskException;
import com.task_manager.task.exceptions.TaskNotFoundException;
import com.task_manager.task.mapper.TaskMapper;
import com.task_manager.task.repository.TaskRepository;
import com.task_manager.task.service.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    public void getAllTasks() {
        /// Given
        var task = Task.builder()
                .title("Test Task")
                .description("Test Description")
                .username("Test User")
                .isCompleted(false)
                .build();

        taskRepository.save(task);


        var responseDto = ResponseDto.builder()
                .username("user1")
                .title("Task 1")
                .description("Description 1")
                .isCompleted(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        when(taskRepository.findAll()).thenReturn(List.of(task));
        when(taskMapper.toResponseDto(task)).thenReturn(responseDto);

        /// WHen
        List<ResponseDto> result = taskService.getAllTasks();

        /// Then
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(taskRepository).findAll();
        verify(taskMapper).toResponseDto(task);
    }

    @Test
    public void createTask() {
        /// Given
        RequestDto requestDto = new RequestDto("user1", "Test Task", "Description", false);
        var task = Task.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .username(requestDto.getUsername())
                .isCompleted(requestDto.getIsCompleted())
                .build();
        when(taskRepository.existsByTitleAndUsername(requestDto.getTitle(), requestDto.getUsername())).thenReturn(false);
        when(taskMapper.toEntity(requestDto)).thenReturn(task);

        /// When
        taskService.createTask(requestDto);

        /// Then
        verify(taskRepository).existsByTitleAndUsername(requestDto.getTitle(), requestDto.getUsername());
        verify(taskMapper).toResponseDto(task);
        verify(taskRepository).save(task);
    }

    @Test
    void createTask_DuplicateTask() {
        /// Given
        var requestDto = new RequestDto("user1", "Test Task", "Description", false);
        when(taskRepository.existsByTitleAndUsername(requestDto.getTitle(), requestDto.getUsername())).thenReturn(true);

        //When/Then
        assertThrows(DuplicateTaskException.class, () -> taskService.createTask(requestDto));
    }

    @Test
    public void deleteTask() {
        /// Given
        Long userId = 1L;

        var task = Task.builder()
                .id(userId)
                .title("Test Task1")
                .description("Description 1")
                .isCompleted(false)
                .build();
        when(taskRepository.findById(userId)).thenReturn(Optional.of(task));

        /// When
        taskService.deleteTask(userId);

        /// Then
        verify(taskRepository).findById(userId);
        verify(taskRepository).delete(task);

    }

    @Test
    public void updateTask_whenTaskNotFound() {
        /// Given
        Long taskId = 99L;
        var requestDto = new RequestDto("user1", "Test Task", "Description", false);
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        /// When/Then
        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(taskId, requestDto));

        //Verify repository was called but save was not
        verify(taskRepository).findById(taskId);
        verify(taskRepository, never()).save(any());

    }

    @Test
    public void getTaskById() {
        var taskId = 1L;
        var task = Task.builder()
                .id(taskId)
                .title("Test Task")
                .description("Test Description")
                .username("Test User")
                .isCompleted(false)
                .build();
        var responseDto = ResponseDto.builder()
                .username("Test User")
                .title("Test Task")
                .description("Test Description")
                .isCompleted(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskMapper.toResponseDto(task)).thenReturn(responseDto);

        /// When
        var result = taskService.getTaskById(taskId);

        /// Then
        assertEquals(responseDto, result);
        verify(taskRepository).findById(taskId);
        verify(taskMapper).toResponseDto(task);
    }

    @Test
    public void getTaskById_whenTaskNotFound() {
        /// Given
        var taskId = 99L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        /// When/Then
        assertThrows(TaskNotFoundException.class, () ->
                taskService.getTaskById(taskId));
        verify(taskRepository).findById(taskId);
        verify(taskMapper, never()).toResponseDto(any());

    }

}


