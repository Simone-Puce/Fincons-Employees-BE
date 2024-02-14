package com.fincons.Handler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(LocalDateTime localDateTime, String message, HttpStatus status, Object responseObj) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("timestamp", localDateTime);
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObj);

        List<String> keys = new ArrayList<>(map.keySet());

        // Sort the list of keys in reverse order
        keys.sort(Collections.reverseOrder());

        // Create a new reverse-sorted map using the sorted keys
        Map<String, Object> sortedMap = new LinkedHashMap<>();
        for (String key : keys) {
            sortedMap.put(key, map.get(key));
        }
        return new ResponseEntity<>(sortedMap, status);
    }
}
