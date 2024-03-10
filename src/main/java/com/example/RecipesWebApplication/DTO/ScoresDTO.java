package com.example.RecipesWebApplication.DTO;

import com.example.RecipesWebApplication.entity.FoodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoresDTO {
    private Integer score;
    private FoodType foodType;
}
