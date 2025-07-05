package com.task_manager.task.controller;


import com.task_manager.task.dto.RequestDto;
import com.task_manager.task.dto.ResponseDto;
import com.task_manager.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity<List<ResponseDto>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    } // ResponseEntity<List<ResponseDto>>
    @GetMapping("/task/user/{username}")
    public ResponseEntity<List<ResponseDto>> findAllTasksByUsername(@PathVariable String username) {
        return ResponseEntity.ok(taskService.findTasksByUsername(username));
    }
    @GetMapping("/task/{id}")
    public ResponseEntity<ResponseDto> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }
    @PostMapping("/task")
    public ResponseEntity<String> createTask(@Valid @RequestBody RequestDto requestDto) {
        taskService.createTask(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Task created successfully");
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<String> updateTask(@PathVariable Long id, @Valid @RequestBody RequestDto requestDto) {
        taskService.updateTask(id, requestDto);
        return ResponseEntity.ok().body("Task updated successfully");
    }

    @PatchMapping("/task/{id}")
    public ResponseEntity<String> partiallyUpdateTask(@PathVariable Long id, @Valid @RequestBody RequestDto requestDto) {
        taskService.partiallyUpdateTask(id, requestDto);
        return ResponseEntity.ok().body("Task partially updated successfully");
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
