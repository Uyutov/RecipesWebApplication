package com.example.RecipesWebApplication.service;

import com.example.RecipesWebApplication.DTO.RecipeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileStorageService {

    private final Path root = Paths.get("images");

    public void saveFiles(List<MultipartFile> files, List<String> steps, String email, Long recipe_id) {
        try {
            Path userPath = Paths.get(root.toString(), email);
            Path recipePath = userPath.resolve(Long.toString(recipe_id));
            Path stepsPath = recipePath.resolve("steps.txt");
            if(!Files.exists(userPath))
            {
                Files.createDirectory(userPath);
            }
            if (Files.exists(recipePath)) {
                FileSystemUtils.deleteRecursively(recipePath.toFile());
            }
            Files.createDirectory(recipePath);
            files.stream().forEach(file -> {
                try {
                    Files.copy(file.getInputStream(), recipePath.resolve(file.getOriginalFilename()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Files.createFile(stepsPath);
            steps.forEach(step -> {
                String separatedStep = step + "@";
                try {
                    Files.writeString(stepsPath, separatedStep, StandardOpenOption.APPEND);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void loadFiles(RecipeDTO dto, String email, Long recipe_id)
    {
        Path recipePath = Paths.get(root.toString(), email, Long.toString(recipe_id));
        Arrays.stream(recipePath.toFile().list()).forEach(name->
        {
            try {
                if (name.equals("steps.txt")) {
                    String cluchedString = Files.readString(recipePath.resolve(name));
                    String splitedString[] = cluchedString.split("@");
                    dto.setSteps(Arrays.stream(splitedString).toList());
                }else{
                    String image_name = recipePath.resolve(name).getFileName().toString();
                    dto.getImages().add(image_name);
                }
            }catch (IOException e)
            {
                System.out.println(e);
            }
        });
    }

    public Resource getImage(Long recipe_id, String email, String image_name)
    {
        Path recipe_path = Paths.get(root.toString(), email, Long.toString(recipe_id), image_name);
        Resource resource;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(recipe_path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resource;
    }

}















