package com.fincons.utility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GenericResponse<T>  {
    private Integer status;
    private boolean success;
    private String message;
    private T data;


    public static <T> GenericResponse<T> empty(String message, Integer status) {
        return success(null, message, status);
    }

    public static <T> GenericResponse<T> success(T data, String message, Integer status) {
        return GenericResponse.<T>builder()
                .message(message)
                .data(data)
                .status(status)
                .success(true)
                .build();
    }
    public static <T> GenericResponse<T> error(String message, Integer status) {
        return GenericResponse.<T>builder()
                .message(message)
                .status(status)
                .success(false)
                .build();
    }
}
