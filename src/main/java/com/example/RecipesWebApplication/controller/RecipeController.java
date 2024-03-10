package com.example.RecipesWebApplication.controller;

import com.example.RecipesWebApplication.DTO.CookDTO;
import com.example.RecipesWebApplication.DTO.RecipeDTO;
import com.example.RecipesWebApplication.DTO.RecipeImageDTO;
import com.example.RecipesWebApplication.DTO.TagsSearchDTO;
import com.example.RecipesWebApplication.entity.Recipe;
import com.example.RecipesWebApplication.service.FileStorageService;
import com.example.RecipesWebApplication.service.RecipeService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/recipes")
@CrossOrigin("*")
public class RecipeController {
    private RecipeService recipeService;
    public RecipeController(RecipeService recipeService, PagedResourcesAssembler<Recipe> pagedResourcesAssembler, FileStorageService fileService) {
        this.recipeService = recipeService;
    }

    @GetMapping()
    public ResponseEntity<PagedModel<EntityModel<RecipeDTO>>> getAllRecipes(Pageable pageable)
    {
        Page<Recipe> page = recipeService.getPageOfRecipes(pageable);
        PagedModel<EntityModel<RecipeDTO>> pagedModel = toRecipePageModel(page);

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/cook")
    public ResponseEntity<PagedModel<EntityModel<RecipeDTO>>> getAllRecipesOfCook(
            @RequestBody CookDTO dto,
            Pageable pageable
    )
    {
        Page<Recipe> page = recipeService.getRecipesOfCook(dto.getEmail(), pageable);
        PagedModel<EntityModel<RecipeDTO>> pagedModel = toRecipePageModel(page);

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<RecipeDTO>> getRecipeById(@PathVariable("id") Long id){
        Recipe recipe = recipeService.getRecipeById(id);
        RecipeDTO recipeDTO = RecipeDTO.from(recipe);

        recipeService.loadFilesToDTO(recipeDTO, recipe.getCook().getEmail(), recipe.getId());

        EntityModel<RecipeDTO> recipeEntity = EntityModel.of(recipeDTO,
                linkTo(methodOn(RecipeController.class).getRecipeById(recipe.getId())).withSelfRel()
        );
        return ResponseEntity.ok(recipeEntity);
    }

    @GetMapping(value = "/images", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getImage(@RequestBody RecipeImageDTO imageDTO)
    {
        Resource resource = recipeService.getImageOfRecipe(imageDTO.getRecipe_id(), imageDTO.getImage_name());
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/tags_search")
    public ResponseEntity<PagedModel<EntityModel<RecipeDTO>>> getRecipesByTags(@RequestBody TagsSearchDTO dto, Pageable pageable)
    {
        Page<Recipe> page = recipeService.getRecipesByTags(dto.getTags(), pageable);
        PagedModel<EntityModel<RecipeDTO>> pagedModel = toRecipePageModel(page);
        return ResponseEntity.ok(pagedModel);
    }
    public PagedModel<EntityModel<RecipeDTO>> toRecipePageModel(Page<Recipe> page)
    {
        List<EntityModel<RecipeDTO>> recipeList = page.getContent().stream().map(
                recipe -> {
                    RecipeDTO dto = RecipeDTO.from(recipe);
                    recipeService.loadFilesToDTO(dto, recipe.getCook().getEmail(), recipe.getId());
                    return EntityModel.of(
                            dto,
                            linkTo(methodOn(RecipeController.class).getRecipeById(recipe.getId())).withSelfRel());
                }
        ).toList();
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements());
        PagedModel<EntityModel<RecipeDTO>> pagedModel = PagedModel.of(
                recipeList, pageMetadata,
                linkTo(methodOn(RecipeController.class).getAllRecipes(page.getPageable())).withSelfRel());
        return pagedModel;
    }
}
