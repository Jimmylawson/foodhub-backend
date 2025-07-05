package com.task_manager.task.mapper;


import com.task_manager.task.dto.RequestDto;
import com.task_manager.task.dto.ResponseDto;
import com.task_manager.task.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public ResponseDto toResponseDto(Task task){
        return ResponseDto.builder()
                .username(task.getUsername())
                .title(task.getTitle())
                .description(task.getDescription())
                .isCompleted(task.isCompleted())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();

    }

    public Task toEntity(RequestDto requestDto){
        return Task.builder()
                .username(requestDto.getUsername())
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .isCompleted(requestDto.getIsCompleted())
                .build();
    }
}
