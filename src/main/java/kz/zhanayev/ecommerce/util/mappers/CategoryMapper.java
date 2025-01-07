package kz.zhanayev.ecommerce.util.mappers;

import kz.zhanayev.ecommerce.dto.CategoryDTO;
import kz.zhanayev.ecommerce.models.Category;

public class CategoryMapper {
    public static CategoryDTO toDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        return categoryDTO;
    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return category;
    }
}
