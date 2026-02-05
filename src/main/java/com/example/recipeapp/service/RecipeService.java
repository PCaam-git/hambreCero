package com.example.recipeapp.service;

import com.example.recipeapp.domain.Ingredient;
import com.example.recipeapp.domain.Recipe;
import com.example.recipeapp.dto.RecipeInDto;
import com.example.recipeapp.dto.RecipeOutDto;
import com.example.recipeapp.exception.IngredientsNotFoundException;
import com.example.recipeapp.exception.RecipeNotFoundException;
import com.example.recipeapp.repository.IngredientRepository;
import com.example.recipeapp.repository.RecipeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final ModelMapper modelMapper;

    public RecipeService(RecipeRepository recipeRepository,
                         IngredientRepository ingredientRepository,
                         ModelMapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.modelMapper = modelMapper;
    }

    private RecipeOutDto toOutDto(Recipe recipe) {
        RecipeOutDto out = modelMapper.map(recipe, RecipeOutDto.class);

        if (recipe.getIngredients() != null) {
            out.ingredientIds = recipe.getIngredients()
                    .stream()
                    .map(Ingredient::getId)
                    .toList();
        }

        return out;
    }

    private List<Ingredient> validateAndLoadIngredients(List<Long> ingredientIds) {
        List<Ingredient> ingredients = ingredientRepository.findAllById(ingredientIds);

        if (ingredients.size() != ingredientIds.size()) {
            // ids pedidos
            List<Long> foundIds = ingredients.stream().map(Ingredient::getId).toList();
            // ids que faltan
            List<Long> missingIds = ingredientIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();

            throw new IngredientsNotFoundException(missingIds);
        }

        return ingredients;
    }

    public List<RecipeOutDto> findAll() {
        return recipeRepository.findAll()
                .stream()
                .map(this::toOutDto)
                .toList();
    }

    public RecipeOutDto create(RecipeInDto inDto) {
        Recipe recipe = new Recipe();
        recipe.setName(inDto.name);
        recipe.setDifficulty(inDto.difficulty);
        recipe.setVegetarian(inDto.vegetarian);
        recipe.setEstimatedCost(inDto.estimatedCost);
        recipe.setLastModified(inDto.lastModified);
        recipe.setServings(inDto.servings);

        List<Ingredient> ingredients = validateAndLoadIngredients(inDto.ingredientIds);
        recipe.setIngredients(ingredients);

        Recipe saved = recipeRepository.save(recipe);
        return toOutDto(saved);
    }

    public RecipeOutDto findById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
        return toOutDto(recipe);
    }

    public RecipeOutDto update(Long id, RecipeInDto inDto) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));

        recipe.setName(inDto.name);
        recipe.setDifficulty(inDto.difficulty);
        recipe.setVegetarian(inDto.vegetarian);
        recipe.setEstimatedCost(inDto.estimatedCost);
        recipe.setLastModified(inDto.lastModified);
        recipe.setServings(inDto.servings);

        List<Ingredient> ingredients = validateAndLoadIngredients(inDto.ingredientIds);
        recipe.setIngredients(ingredients);

        Recipe saved = recipeRepository.save(recipe);
        return toOutDto(saved);
    }

    public void delete(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));

        recipeRepository.delete(recipe);
    }
}
