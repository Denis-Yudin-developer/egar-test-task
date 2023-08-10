package com.denisyudin.CarGuide.service.impl;

import com.denisyudin.CarGuide.entity.Category;
import com.denisyudin.CarGuide.repository.CategoryRepository;
import com.denisyudin.CarGuide.rest.exceptions.BadRequestException;
import com.denisyudin.CarGuide.service.CategoryService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final String CATEGORY_ALREADY_EXISTS = "Категория с таким названием уже существует";

    public Category create(@NonNull String categoryName) {
        categoryRepository.findByCategoryName(categoryName)
                .ifPresent(found -> {
                    throw new BadRequestException(CATEGORY_ALREADY_EXISTS);
                });
        Category toSave = new Category(categoryName);
        return categoryRepository.save(toSave);
    }
    @Override
    @Transactional
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Category> getByCategoryName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }
}
