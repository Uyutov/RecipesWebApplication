package com.example.RecipesWebApplication.DTO;

import com.example.RecipesWebApplication.entity.Score;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatedScoreDTO {
    private String foodType;
    private Integer score;

    public CreatedScoreDTO(Score score)
    {
        this.foodType = score.getFoodType().getName();
        this.score = score.getScore();
    }
}
