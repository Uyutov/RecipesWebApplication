package com.example.RecipesWebApplication.service;

import com.example.RecipesWebApplication.entity.Cook;
import com.example.RecipesWebApplication.entity.FoodType;
import com.example.RecipesWebApplication.entity.Score;
import com.example.RecipesWebApplication.repository.ScoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final FoodTypeService foodTypeService;
    private final CookService cookService;
    public ScoreService(ScoreRepository scoreRepository, FoodTypeService foodTypeService, CookService cookService) {
        this.scoreRepository = scoreRepository;
        this.foodTypeService = foodTypeService;
        this.cookService = cookService;
    }

    public List<Score> getAllScoresOfCook(String username)
    {
        Cook cook = cookService.getCookByUsername(username);
        List<Score> scores = foodTypeService.getAllFoodTypes().stream().map(foodType ->
        {
            Optional<Score> score = scoreRepository.getScoreByCookIdAndFoodTypeId(cook.getId(), foodType.getId());
            return score.isPresent() ? score.get() : new Score(0L, 100, null, foodType);
        }).toList();
        return scores;
    }

    public Score updateScore(String username, String foodTypeName, String grade)
    {
        FoodType foodType = foodTypeService.getFoodTypeByName(foodTypeName);
        Cook cook = cookService.getCookByUsername(username);
        Optional<Score> score = scoreRepository.getScoreByCookIdAndFoodTypeId(cook.getId(), foodType.getId());
        if(score.isPresent())
        {
            Score updatedScore = score.get();
            updatedScore.setScore(getValueOfGrade(grade));
            return scoreRepository.save(updatedScore);
        }else{
            Score newScore = new Score(0L, getValueOfGrade(grade), cook , foodType);
            return scoreRepository.save(newScore);
        }
    }

    public static Integer getValueOfGrade(String grade)
    {
        switch (grade)
        {
            case("A"):
                return 200;
            case ("B"):
                return 150;
            case("C"):
                return 100;
            case("D"):
                return 50;
            case ("F"):
                return 0;
        }
        return 0;
    }
}
