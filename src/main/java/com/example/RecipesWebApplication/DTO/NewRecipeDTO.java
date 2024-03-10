package com.example.RecipesWebApplication.DTO;

import com.example.RecipesWebApplication.entity.Ingredient;
import com.example.RecipesWebApplication.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewRecipeDTO {
    private String name;
    private List<Ingredient> ingredients;
    private List<String> steps;
    private List<Tag> tags;
}
