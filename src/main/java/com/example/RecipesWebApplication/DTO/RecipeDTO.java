package com.example.RecipesWebApplication.DTO;

import com.example.RecipesWebApplication.entity.Ingredient;
import com.example.RecipesWebApplication.entity.Recipe;
import com.example.RecipesWebApplication.entity.Tag;
import com.example.RecipesWebApplication.entity.enums.Tags;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {
    private String name;
    private String cookEmail;
    private List<IngredientDTO> ingredients;
    private List<String> images = new ArrayList<>();
    private List<String> steps = new ArrayList<>();
    private List<Tag> tags;

    public static RecipeDTO from(Recipe recipe)
    {
        RecipeDTO recipeDTO = new RecipeDTO();

        recipeDTO.name = recipe.getName();
        recipeDTO.cookEmail = recipe.getCook().getEmail();
        recipeDTO.ingredients = recipe.getIngredients().stream().map(IngredientDTO::from).toList();
        recipeDTO.tags = recipe.getTags();

        return recipeDTO;
    }

}
