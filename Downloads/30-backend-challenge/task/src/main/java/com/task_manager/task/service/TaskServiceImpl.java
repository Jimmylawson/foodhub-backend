package com.task_manager.task.service;

import com.task_manager.task.dto.RequestDto;
import com.task_manager.task.dto.ResponseDto;
import com.task_manager.task.entity.Task;
import com.task_manager.task.exceptions.DuplicateTaskException;
import com.task_manager.task.exceptions.TaskNotFoundException;
import com.task_manager.task.mapper.TaskMapper;
import com.task_manager.task.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;


    @Override
    public List<ResponseDto> getAllTasks() {
        return  taskRepository.findAll()
                .stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<ResponseDto> findTasksByUsername(String username) {
        return taskRepository.findAllByUsername(username)
                .stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }

    @Override
    public ResponseDto getTaskById(Long id) {
       return  taskMapper.toResponseDto(findTaskEntityById(id));

    }

    @Override
    public void deleteTask(Long id) {
        var task = findTaskEntityById(id);
        taskRepository.delete(task);

    }

    @Override
    public void createTask(RequestDto requestDto) {
        if(taskRepository.existsByTitleAndUsername(requestDto.getTitle(), requestDto.getUsername())){
            throw new DuplicateTaskException("Task already exists");
        }
        taskRepository.save(taskMapper.toEntity(requestDto));

    }

    @Override
    @Transactional
    public void updateTask(Long id, RequestDto dto) {
        Task existingTask = findTaskEntityById(id);
        existingTask.setTitle(dto.getTitle());
        existingTask.setDescription(dto.getDescription());
        existingTask.setCompleted(dto.getIsCompleted());
        existingTask.setUsername(dto.getUsername());

        taskRepository.save(existingTask);


    }

    @Override
    public void partiallyUpdateTask(Long id, RequestDto requestDto) {
        var task = findTaskEntityById(id);

        if (requestDto.getTitle() != null) {
            task.setTitle(requestDto.getTitle());
        }

        if (requestDto.getDescription() != null) {
            task.setDescription(requestDto.getDescription());
        }

     if(requestDto.getIsCompleted() != null){
         task.setCompleted(requestDto.getIsCompleted());
     }
        if (requestDto.getUsername() != null) {
            task.setUsername(requestDto.getUsername());
        }

        taskRepository.save(task);

    }

    private Task findTaskEntityById(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }
    public ResponseDto findTaskById(Long id){
        return  taskMapper.toResponseDto(taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found")));
    }


}
