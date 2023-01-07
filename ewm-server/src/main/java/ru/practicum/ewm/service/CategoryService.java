package ru.practicum.ewm.service;

import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto create(CategoryDto categoryDto);

    CategoryDto update(CategoryDto categoryDto);

    void delete(long categoryId);

    List<CategoryDto> getAll(int from, int size);

    CategoryDto getById(long id);

    Category getCategoryById(long id);
}
