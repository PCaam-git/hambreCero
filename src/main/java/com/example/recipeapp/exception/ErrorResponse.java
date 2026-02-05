package com.example.recipeapp.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

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
    //404 para detalles (missing id en receta por ejemplo en lista ingredientes)
    public static ErrorResponse notFound(String message, Map<String, String> errors) {
        return new ErrorResponse(404, "not-found", message, errors);
    }
}
