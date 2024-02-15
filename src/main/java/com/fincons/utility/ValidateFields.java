package com.fincons.utility;

import com.fincons.exception.IllegalArgumentException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateFields {

    private String field;

    public static void validateSingleField(String field){
        if (field == null){
            throw new IllegalArgumentException("The field can't be null");
        }
        if (field.isEmpty()){
            throw new IllegalArgumentException("The field can't be empty");
        }
    }

}
