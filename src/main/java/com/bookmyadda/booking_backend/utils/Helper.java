package com.bookmyadda.booking_backend.utils;

import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class Helper {
    private final ObjectMapper objectMapper;

    public Helper(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }
    // JSON helper
    public String toJson(Object obj) {

        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("JSON conversion failed");
        }
    }
}
