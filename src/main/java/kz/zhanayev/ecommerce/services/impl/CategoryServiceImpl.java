package kz.zhanayev.ecommerce.services.impl;

import kz.zhanayev.ecommerce.dto.CategoryDTO;
import kz.zhanayev.ecommerce.exceptions.NotFoundException;
import kz.zhanayev.ecommerce.models.Category;
import kz.zhanayev.ecommerce.repositories.CategoryRepository;
import kz.zhanayev.ecommerce.services.CategoryService;
import kz.zhanayev.ecommerce.util.mappers.CategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Категория не найдена с идентификатором: " + id));
        return CategoryMapper.toDTO(category);
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category category = CategoryMapper.toEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.toDTO(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Категория не найдена с идентификатором: " + id));
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        Category updatedCategory = categoryRepository.save(category);
        return CategoryMapper.toDTO(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Категория не найдена с идентификатором: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
