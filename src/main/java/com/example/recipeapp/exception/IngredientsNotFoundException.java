package com.example.recipeapp.exception;

import java.util.List;

public class IngredientsNotFoundException extends RuntimeException {

    private final List<Long> missingIds;

    public IngredientsNotFoundException(List<Long> missingIds) {
        super("Ingredient IDs not found: " + missingIds);
        this.missingIds = missingIds;
    }

    public List<Long> getMissingIds() {
        return missingIds;
    }
}
