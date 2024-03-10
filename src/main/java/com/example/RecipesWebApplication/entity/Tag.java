package com.example.RecipesWebApplication.entity;

import com.example.RecipesWebApplication.entity.enums.Tags;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="tag_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private Tags name;
}
