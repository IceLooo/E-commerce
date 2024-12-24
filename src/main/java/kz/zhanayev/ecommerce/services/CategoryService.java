package kz.zhanayev.ecommerce.services;

import kz.zhanayev.ecommerce.dto.CategoryDTO;
import java.util.List;

public interface CategoryService {

     List<CategoryDTO> getAllCategories();
     CategoryDTO getCategoryById(Long id);
     CategoryDTO saveCategory(CategoryDTO categoryDTO);
     CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
     void deleteCategory(Long id);
}
