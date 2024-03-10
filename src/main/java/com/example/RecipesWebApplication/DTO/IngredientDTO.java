package com.example.RecipesWebApplication.DTO;

import com.example.RecipesWebApplication.entity.FoodType;
import com.example.RecipesWebApplication.entity.Ingredient;
import com.example.RecipesWebApplication.entity.enums.Units;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDTO {
    private Float amount;
    private FoodType foodType;
    private String unit;

    public static IngredientDTO from(Ingredient ingredient)
    {
        IngredientDTO ingredientDTO = new IngredientDTO();

        ingredientDTO.amount = ingredient.getAmount();
        ingredientDTO.foodType = ingredient.getFoodType();
        ingredientDTO.unit = ingredient.getUnit().name();

        return ingredientDTO;
    }
}
