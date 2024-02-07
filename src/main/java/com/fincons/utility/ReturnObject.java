package com.fincons.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnObject {

    private String message;
    private List<Long> list;



    public ReturnObject(String message) {
        this.message = message;
    }


}
