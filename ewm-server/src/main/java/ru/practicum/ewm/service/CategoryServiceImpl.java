package ru.practicum.ewm.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.mapper.CategoryMapper;
import ru.practicum.ewm.mapper.PageMapper;
import ru.practicum.ewm.model.Category;
import ru.practicum.ewm.model.dto.CategoryDto;
import ru.practicum.ewm.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toCategory(categoryDto)));
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toCategory(categoryDto)));
    }

    @Override
    public void delete(long categoryId) {
        Category category = categoryRepository.getReferenceById(categoryId);
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDto> getAll(int from, int size) {
        Page<Category> categoryPage = categoryRepository.findAll(PageMapper.getPagable(from, size));

        List<Category> categoryList = new ArrayList<>();
        if (categoryPage != null && categoryPage.hasContent()) {
            categoryList = categoryPage.getContent();
        }
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryDtoList.add(categoryMapper.toDto(category));
        }

        return categoryDtoList;
    }

    @Override
    public CategoryDto getById(long id) {
        return categoryMapper.toDto(categoryRepository.getReferenceById(id));
    }

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.getReferenceById(id);
    }
}
