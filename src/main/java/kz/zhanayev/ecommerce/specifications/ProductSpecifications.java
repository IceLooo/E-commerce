package kz.zhanayev.ecommerce.specifications;

import kz.zhanayev.ecommerce.models.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public interface ProductSpecifications {
    Specification<Product> hasName(String name);
    Specification<Product> hasCategory(String categoryName);
    Specification<Product> hasPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    Specification<Product> isInStock(Boolean inStock);
}
