package com.task_manager.task.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter @Setter
public class RequestDto {
    @NotBlank(message= "username is required")
    private String username;
    @NotBlank(message= "title is required")
    private String title;
    @NotBlank(message= "description is required")
    private String description;
    private Boolean isCompleted;

}
