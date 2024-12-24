package kz.zhanayev.ecommerce.repositories;

import kz.zhanayev.ecommerce.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
