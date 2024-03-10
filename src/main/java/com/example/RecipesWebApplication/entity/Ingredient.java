package com.example.RecipesWebApplication.entity;

import com.example.RecipesWebApplication.entity.enums.Units;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
    @Enumerated(EnumType.STRING)
    private Units unit;
    @ManyToOne(fetch=FetchType.EAGER, cascade= {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "food_type_id")
    private FoodType foodType;
    Float amount;

    public Ingredient(FoodType foodType, Float amount, Units unit) {
        this.foodType = foodType;
        this.amount = amount;
        this.unit = unit;
    }

}
