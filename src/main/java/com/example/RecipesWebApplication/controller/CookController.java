package com.example.RecipesWebApplication.controller;

import com.example.RecipesWebApplication.DTO.*;
import com.example.RecipesWebApplication.entity.Recipe;
import com.example.RecipesWebApplication.entity.Score;
import com.example.RecipesWebApplication.service.FileStorageService;
import com.example.RecipesWebApplication.service.RecipeService;
import com.example.RecipesWebApplication.service.ScoreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/cook")
@CrossOrigin("*")
public class CookController {
    private final RecipeService recipeService;
    private final ScoreService scoreService;

    public CookController(RecipeService recipeService, ScoreService scoreService, FileStorageService fileService) {
        this.recipeService = recipeService;
        this.scoreService = scoreService;
    }

    /*@GetMapping("/recipes")
    public ResponseEntity<PagedModel<EntityModel<RecipeDTO>>> getAllRecipesOfCook(
            @AuthenticationPrincipal Jwt principal,
            Pageable pageable)
    {
        Page<Recipe> page = recipeService.getRecipesOfCook(principal.getClaim("sub"), pageable);

        PagedModel<EntityModel<RecipeDTO>> pagedModel = RecipeController.toRecipePageModel(page);
        return ResponseEntity.ok(pagedModel);
    }*/

    @PostMapping("/recipes")
    public ResponseEntity<EntityModel<Recipe>> createRecipe(
            @AuthenticationPrincipal Jwt principal,
            @RequestPart("images") List<MultipartFile> images,
            @RequestPart("recipe") NewRecipeDTO recipe)
    {
        String email = principal.getClaim("sub");
        Recipe createdRecipe = recipeService.createNewRecipe(email, recipe);
        recipeService.saveFiles(images, recipe.getSteps(), email, createdRecipe.getId());
        return null;
    }

    @DeleteMapping("/recipes")
    public ResponseEntity<ResponseDTO> deleteRecipe(
            @AuthenticationPrincipal Jwt principal,
            @RequestBody DeleteRecipeDTO recipeDTO
    )
    {
        return recipeService.deleteRecipe(principal.getClaim("sub"), recipeDTO)
                ? null
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/scores")
    public ResponseEntity<CollectionModel<ScoresDTO>> getAllScoresOfCook(@AuthenticationPrincipal Jwt principal){
        List<Score> scores = scoreService.getAllScoresOfCook(principal.getClaim("sub"));
        List<ScoresDTO> dtos = scores.stream().map(score -> new ScoresDTO(score.getScore(), score.getFoodType())).toList();
        CollectionModel<ScoresDTO> collectionModel = CollectionModel.of(dtos,
                linkTo(
                        methodOn(CookController.class).getAllScoresOfCook(principal)
                ).withSelfRel());
        return ResponseEntity.ok(collectionModel);
    }
    @PatchMapping("/scores")
    public ResponseEntity<EntityModel<CreatedScoreDTO>> updateScore(@RequestBody NewScoreDTO dto, @AuthenticationPrincipal Jwt principal){
        Score score = scoreService.updateScore(principal.getClaim("sub"), dto.getFoodType(), dto.getGrade());
        CreatedScoreDTO newScore = new CreatedScoreDTO(score);
        EntityModel<CreatedScoreDTO> scoreModel = EntityModel.of(newScore,
                linkTo(methodOn(CookController.class).getAllScoresOfCook(principal)).withSelfRel()
        );
        return ResponseEntity.ok(scoreModel);
    }
}
