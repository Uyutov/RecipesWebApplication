package com.example.RecipesWebApplication.repository;

import com.example.RecipesWebApplication.entity.Ingredient;
import com.example.RecipesWebApplication.entity.IngredientKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, IngredientKey> {
}
