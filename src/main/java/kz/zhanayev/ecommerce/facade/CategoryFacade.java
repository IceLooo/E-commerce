package kz.zhanayev.ecommerce.facade;

import kz.zhanayev.ecommerce.dto.CategoryDTO;
import kz.zhanayev.ecommerce.models.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryFacade {

    // Преобразование из Category в CategoryDTO
    public CategoryDTO toDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        return categoryDTO;
    }

    // Преобразование из CategoryDTO в Category
    public Category toEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return category;
    }
}
