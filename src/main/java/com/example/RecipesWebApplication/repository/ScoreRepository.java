package com.example.RecipesWebApplication.repository;

import com.example.RecipesWebApplication.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    public Optional<Score> getScoreByCookIdAndFoodTypeId(Long cook_id, Long food_type_id);
}
