package com.example.RecipesWebApplication.repository;

import com.example.RecipesWebApplication.entity.Cook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CookRepository extends JpaRepository<Cook, Long> {
    Optional<Cook> findOneByEmail(String email);
}
