package com.example.recipeapp.exception;

import java.util.HashMap;
import lombok.Data;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {
    
    public int code;
    public String title;
    public String message;
    private Map<String, String> errors;

    public static ErrorResponse generalError(int code, String title, String message) {
        return new ErrorResponse(code, title, message, new HashMap<>());
    }

    public static ErrorResponse notFound(String message) {
        return new ErrorResponse(404, "not-found", message, new HashMap<>());
    }

    public static ErrorResponse validationError(Map<String, String> errors) {
        return new ErrorResponse(400, "bad-request", "Validation error", errors);
    }
}

