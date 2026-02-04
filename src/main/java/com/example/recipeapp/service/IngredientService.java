package com.example.recipeapp.service;

import com.example.recipeapp.domain.Ingredient;
import com.example.recipeapp.dto.IngredientInDto;
import com.example.recipeapp.dto.IngredientOutDto;
import com.example.recipeapp.exception.IngredientNotFoundException;
import com.example.recipeapp.repository.IngredientRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private ModelMapper modelMapper;

    // GET ALL (Con filtros)
    public List<IngredientOutDto> findAll(String name, String season) {
        List<Ingredient> ingredients;

        if (name != null && !name.isEmpty()) {
            ingredients = ingredientRepository.findByNameContainingIgnoreCase(name);
        } else if (season != null && !season.isEmpty()) {
            Ingredient.Season seasonEnum = Ingredient.Season.valueOf(season.toUpperCase());
            ingredients = ingredientRepository.findBySeason(seasonEnum);
        } else {
            ingredients = ingredientRepository.findAll();
        }

        return modelMapper.map(ingredients, new TypeToken<List<IngredientOutDto>>() {}.getType());
    }
    // GET by Id
    public IngredientOutDto findById(long id) throws IngredientNotFoundException {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(IngredientNotFoundException::new);

        return modelMapper.map(ingredient, IngredientOutDto.class);
    }

        // POST
    public IngredientOutDto add(IngredientInDto ingredientInDto) {
        Ingredient ingredient = modelMapper.map(ingredientInDto, Ingredient.class);

        if (ingredientInDto.getSeason() != null) {
            ingredient.setSeason(Ingredient.Season.valueOf(ingredientInDto.getSeason().toUpperCase()));
        }

        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return modelMapper.map(savedIngredient, IngredientOutDto.class);
    }

 // PUT 
    public IngredientOutDto modify(long id, IngredientInDto ingredientInDto) throws IngredientNotFoundException {
        Ingredient existingIngredient = ingredientRepository.findById(id)
                .orElseThrow(IngredientNotFoundException::new);

        modelMapper.map(ingredientInDto, existingIngredient);
        
        existingIngredient.setId(id);

        if (ingredientInDto.getSeason() != null) {
            existingIngredient.setSeason(Ingredient.Season.valueOf(ingredientInDto.getSeason().toUpperCase()));
        }

        Ingredient savedIngredient = ingredientRepository.save(existingIngredient);
        return modelMapper.map(savedIngredient, IngredientOutDto.class);
    }   

    // DELETE
    public void delete(long id) throws IngredientNotFoundException {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(IngredientNotFoundException::new);

        ingredientRepository.delete(ingredient);
    }
    }
