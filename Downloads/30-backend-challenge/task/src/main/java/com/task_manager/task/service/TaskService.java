package com.task_manager.task.service;

import com.task_manager.task.dto.RequestDto;
import com.task_manager.task.dto.ResponseDto;

import java.util.List;

public interface TaskService {
    List<ResponseDto> getAllTasks();
    List<ResponseDto> findTasksByUsername(String username);
    //List<ResponseDto>
    ResponseDto getTaskById(Long id);
    void deleteTask(Long id);
    void createTask(RequestDto requestDto);
    void updateTask(Long id, RequestDto requestDto);
    void  partiallyUpdateTask(Long id, RequestDto requestDto);


}
