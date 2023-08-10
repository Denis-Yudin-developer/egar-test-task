package com.denisyudin.CarGuide.service;

import com.denisyudin.CarGuide.entity.Category;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category create(@Nonnull String categoryName);
    List<Category> getAll();
    Optional<Category> getByCategoryName(String categoryName);
}
