package com.example.RecipesWebApplication.service;

import com.example.RecipesWebApplication.DTO.DeleteRecipeDTO;
import com.example.RecipesWebApplication.DTO.NewRecipeDTO;
import com.example.RecipesWebApplication.DTO.RecipeDTO;
import com.example.RecipesWebApplication.entity.Cook;
import com.example.RecipesWebApplication.entity.Recipe;
import com.example.RecipesWebApplication.entity.Ingredient;
import com.example.RecipesWebApplication.entity.Tag;
import com.example.RecipesWebApplication.entity.enums.Tags;
import com.example.RecipesWebApplication.repository.RecipeRepository;
import com.example.RecipesWebApplication.repository.TagRepository;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final CookService cookService;
    private final IngredientService ingredientService;
    private final FileStorageService fileService;
    private final FoodTypeService foodTypeService;
    private final TagRepository tagRepository;
    public RecipeService(RecipeRepository recipeRepository, CookService cookService, IngredientService ingredientService, FileStorageService fileService, FoodTypeService foodTypeService, TagRepository tagRepository) {
        this.recipeRepository = recipeRepository;
        this.cookService = cookService;
        this.ingredientService = ingredientService;
        this.fileService = fileService;
        this.foodTypeService = foodTypeService;
        this.tagRepository = tagRepository;
    }

    public Page<Recipe> getPageOfRecipes(Pageable pageable)
    {
        PageRequest request = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.DESC,"name"))
        );
        return recipeRepository.findAll(request);
    }
    public Recipe getRecipeById(Long id)
    {
        Recipe recipe = recipeRepository.findById(id).get();
        return recipe;
    }

    public Page<Recipe> getRecipesOfCook(String username, Pageable pageable){
        PageRequest request = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.DESC,"name"))
        );
        Cook cook = cookService.getCookByUsername(username);
        return recipeRepository.findByCook_id(cook.getId(), request);
    }

    public Recipe createNewRecipe(String username, NewRecipeDTO recipe)
    {
        Cook cook = cookService.getCookByUsername(username);
        Recipe newRecipe = new Recipe(cook, recipe.getName());
        recipe.getIngredients().stream().forEach(ingredient ->
        {
            Ingredient newIngredient = new Ingredient(
                    foodTypeService.getFoodTypeByName(ingredient.getFoodType().getName()),
                    ingredient.getAmount(),
                    ingredient.getUnit()
            );
            newRecipe.addIngredient(newIngredient);
        });
        List<Tag> tags = new ArrayList<>();
        recipe.getTags().stream().forEach(tag -> {
            tags.add(tagRepository.getTagByName(tag.getName()).get());
        });
        newRecipe.setTags(tags);
        return recipeRepository.save(newRecipe);
    }

    public Page<Recipe> getRecipesByTags(List<String> tags, Pageable pageable)
    {
        PageRequest request = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize()
        );
        List<Long> tags_ids = tags.stream().map(tag -> tagRepository.getTagByName(Tags.valueOf(tag)).get().getId()).toList();
        Page<Recipe> recipes = recipeRepository.searchByTagsAndWithPagination(tags_ids, tags_ids.size(), request);
        return recipes;
    }

    public Boolean deleteRecipe(String username, DeleteRecipeDTO recipe){
        Cook cook = cookService.getCookByUsername(username);
        return recipeRepository.deleteByCook_idAndId(cook.getId(), recipe.getRecipe_id()) != 0L;
    }

    public void loadFilesToDTO(RecipeDTO dto, String email, Long recipe_id)
    {
        fileService.loadFiles(dto, email, recipe_id);
    }

    public void saveFiles(List<MultipartFile> files, List<String> steps, String email, Long recipe_id)
    {
        fileService.saveFiles(files, steps, email, recipe_id);
    }

    public Resource getImageOfRecipe(Long recipe_id, String image_name)
    {
        Recipe recipe = recipeRepository.findById(recipe_id).get();
        Resource image = fileService.getImage(recipe_id, recipe.getCook().getEmail(), image_name);
        return image;
    }
}












