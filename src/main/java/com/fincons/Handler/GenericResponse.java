package com.fincons.Handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class GenericResponse<T> {
    private LocalDateTime localDateTime;
    private String message;
    private String status;
    private T data;

}
