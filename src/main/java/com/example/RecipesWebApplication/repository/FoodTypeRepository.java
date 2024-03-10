package com.example.RecipesWebApplication.repository;

import com.example.RecipesWebApplication.entity.FoodType;
import com.example.RecipesWebApplication.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodTypeRepository extends JpaRepository<FoodType, Long> {
    public Optional<FoodType> getFoodTypeByName(String name);
}
