package com.example.springbootwebtutorial.springbootwebtutorial.advices;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class APIResponse<T> {
    private LocalDateTime timestamp;


    private T data;
    private APIError error;

    public APIResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public APIResponse(APIError error) {
        this();
        this.error = error;
    }

    public APIResponse(T data) {
        this();
        this.data = data;
    }
}
