package kz.zhanayev.ecommerce.services.impl;

import kz.zhanayev.ecommerce.dto.CategoryDTO;
import kz.zhanayev.ecommerce.exceptions.CategoryNotFoundException;
import kz.zhanayev.ecommerce.facade.CategoryFacade;
import kz.zhanayev.ecommerce.models.Category;
import kz.zhanayev.ecommerce.repositories.CategoryRepository;
import kz.zhanayev.ecommerce.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryFacade categoryFacade;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryFacade categoryFacade) {
        this.categoryRepository = categoryRepository;
        this.categoryFacade = categoryFacade;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryFacade::toDTO) // Используем фасад
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));
        return categoryFacade.toDTO(category); // Используем фасад
    }


    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category category = categoryFacade.toEntity(categoryDTO); // Используем фасад
        Category savedCategory = categoryRepository.save(category);
        return categoryFacade.toDTO(savedCategory); // Используем фасад
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        Category updatedCategory = categoryRepository.save(category);
        return categoryFacade.toDTO(updatedCategory); // Используем фасад
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
