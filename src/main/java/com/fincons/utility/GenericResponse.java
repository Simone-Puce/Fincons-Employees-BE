package com.fincons.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse<T>  {

    private HttpStatus status;
    private boolean success;
    private String message;
    private T data;

    public GenericResponse(HttpStatus status, boolean success, String message) {
        this.status = status;
        this.success = success;
        this.message = message;
    }
    public GenericResponse(HttpStatus status, boolean success, T data) {
        this.status = status;
        this.success = success;
        this.data = data;
    }

}
