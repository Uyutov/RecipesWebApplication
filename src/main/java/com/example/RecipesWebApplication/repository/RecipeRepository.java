package com.example.RecipesWebApplication.repository;

import com.example.RecipesWebApplication.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>, PagingAndSortingRepository<Recipe, Long> {
    Page<Recipe> findByCook_id(Long cook_id, Pageable pageable);
    @Transactional
    long deleteByCook_idAndId(Long cook_id, Long id);
    @Query(value = "Select recipe.* from recipe left join recipe_tag on recipe.recipe_id = recipe_tag.recipe_id left join tag on tag.tag_id = recipe_tag.tag_id where tag.tag_id in ?1 group by recipe.recipe_id having count(*) = ?2 order by recipe.name",
            countQuery = "Select count(recipe.*) from recipe left join recipe_tag on recipe.recipe_id = recipe_tag.recipe_id left join tag on tag.tag_id = recipe_tag.tag_id where tag.tag_id in ?1 group by recipe.recipe_id having count(*) = ?2 order by recipe.name",
            nativeQuery = true)
    Page<Recipe> searchByTagsAndWithPagination(List<Long> ids, int amount, Pageable pageable);

}
