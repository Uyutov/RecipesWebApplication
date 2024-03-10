package com.example.RecipesWebApplication.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeImageDTO {
    private Long recipe_id;
    private String image_name;
}
