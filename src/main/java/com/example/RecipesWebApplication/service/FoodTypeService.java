package com.example.RecipesWebApplication.service;

import com.example.RecipesWebApplication.entity.FoodType;
import com.example.RecipesWebApplication.repository.FoodTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodTypeService {
    private final FoodTypeRepository foodTypeRepository;

    public FoodTypeService(FoodTypeRepository foodTypeRepository) {
        this.foodTypeRepository = foodTypeRepository;
    }

    public FoodType getFoodTypeById(Long id)
    {
        return foodTypeRepository.findById(id).get();
    }
    public FoodType getFoodTypeByName(String name)
    {
        return foodTypeRepository.getFoodTypeByName(name).get();
    }
    public List<FoodType> getAllFoodTypes()
    {
        return foodTypeRepository.findAll();
    }
}