package com.example.RecipesWebApplication.controller;

import com.example.RecipesWebApplication.entity.FoodType;
import com.example.RecipesWebApplication.entity.Ingredient;
import com.example.RecipesWebApplication.service.FoodTypeService;
import com.example.RecipesWebApplication.service.IngredientService;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/ingredients")
@CrossOrigin("*")
public class FoodTypeController {
    private final FoodTypeService foodTypeService;

    public FoodTypeController(FoodTypeService foodTypeService) {
        this.foodTypeService = foodTypeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<FoodType>> getFoodTypeById(@PathVariable("id") Long id)
    {
        FoodType foodType = foodTypeService.getFoodTypeById(id);
        EntityModel<FoodType> foodTypeEntity = EntityModel.of(
                foodType,
                linkTo(methodOn(FoodTypeController.class).getFoodTypeById(foodType.getId())).withSelfRel()
        );
        return ResponseEntity.ok(foodTypeEntity);
    }
}
