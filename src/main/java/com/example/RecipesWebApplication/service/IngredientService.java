package com.example.RecipesWebApplication.service;

import com.example.RecipesWebApplication.entity.Ingredient;
import com.example.RecipesWebApplication.repository.IngredientRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final FoodTypeService foodTypeService;
    private final RecipeService recipeService;
    public IngredientService(IngredientRepository repository, @Lazy FoodTypeService foodTypeService,@Lazy RecipeService recipeService) {
        this.ingredientRepository = repository;
        this.foodTypeService = foodTypeService;
        this.recipeService = recipeService;
    }
}
