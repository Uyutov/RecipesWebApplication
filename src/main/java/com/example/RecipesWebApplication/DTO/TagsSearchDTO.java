package com.example.RecipesWebApplication.DTO;

import com.example.RecipesWebApplication.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagsSearchDTO {
    List<String> tags;
}
