package com.fincons.utility;

import com.fincons.exception.IllegalArgumentException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateSingleField {

    private String field;

    public static void validateSingleField(String field){
        if (field.isEmpty()){
            throw new IllegalArgumentException("The field can't be null or empty");
        }
    }
}
