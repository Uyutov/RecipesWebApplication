package com.example.RecipesWebApplication.repository;

import com.example.RecipesWebApplication.entity.Tag;
import com.example.RecipesWebApplication.entity.enums.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    public Optional<Tag> getTagByName(Tags name);
}
