package com.food_delivery.zomato_backend.dtos.error;

import lombok.*;


import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponseDto {
    private String apiPath;
    private HttpStatus code;
    private String message;
    private LocalDateTime timestamp;

}
