package com.example.recipeapp.repository;

import com.example.recipeapp.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findAll();

    // Filtros
    List<Ingredient> findByNameContainingIgnoreCase(String name);
    List<Ingredient> findBySeason(Ingredient.Season season);
}
