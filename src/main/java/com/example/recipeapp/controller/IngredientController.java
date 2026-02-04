package com.example.recipeapp.controller;

import com.example.recipeapp.dto.IngredientInDto;
import com.example.recipeapp.dto.IngredientOutDto;
import com.example.recipeapp.exception.ErrorResponse;
import com.example.recipeapp.exception.IngredientNotFoundException;
import com.example.recipeapp.service.IngredientService;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class IngredientController {
    
    @Autowired
    private IngredientService ingredientService;

    //GET ALL (Con filtros)
    @GetMapping("/ingredients")
    public ResponseEntity<List<IngredientOutDto>> getAll(
        @RequestParam(value = "name", defaultValue = "") String name,
        @RequestParam(value = "season", defaultValue = "") String season) {

            List<IngredientOutDto> ingredients = ingredientService.findAll(name, season);
            return ResponseEntity.ok(ingredients);
        }

        // GET by Id
    @GetMapping("/ingredients/{id}")
    public ResponseEntity<IngredientOutDto> get(@PathVariable long id) throws IngredientNotFoundException {
        IngredientOutDto ingredientOutDto = ingredientService.findById(id);
        return ResponseEntity.ok(ingredientOutDto);
    }

    // POST
    @PostMapping("/ingredients")
    public ResponseEntity<IngredientOutDto> addIngredient(@Valid @RequestBody IngredientInDto ingredientInDto) {
        IngredientOutDto newIngredient = ingredientService.add(ingredientInDto);
        return new ResponseEntity<>(newIngredient, HttpStatus.CREATED);
    }

    // PUT
    @PutMapping("/ingredients/{id}")
    public ResponseEntity<IngredientOutDto> modifyIngredient(@PathVariable long id, @Valid @RequestBody IngredientInDto ingredientInDto) 
        throws IngredientNotFoundException {

        IngredientOutDto updatedIngredient = ingredientService.modify(id, ingredientInDto);
        return ResponseEntity.ok(updatedIngredient);
    }

    // DELETE
    @DeleteMapping("/ingredients/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable long id) throws IngredientNotFoundException {
        ingredientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 404 - Ingredient not found
    @ExceptionHandler(IngredientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(IngredientNotFoundException infe) {
        ErrorResponse errorResponse = ErrorResponse.notFound("The ingredient was not found.");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // 400 - Validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException manve) {
        Map<String, String> errors = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        ErrorResponse errorResponse = ErrorResponse.validationError(errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
