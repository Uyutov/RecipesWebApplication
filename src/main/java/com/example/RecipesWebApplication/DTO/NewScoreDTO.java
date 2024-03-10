package com.example.RecipesWebApplication.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewScoreDTO {
    private String foodType;
    private String grade;
}
