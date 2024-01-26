package com.fincons.utility;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse<T>  {

    private HttpStatus status;
    private boolean success;
    private String message;
    private T data;

}
