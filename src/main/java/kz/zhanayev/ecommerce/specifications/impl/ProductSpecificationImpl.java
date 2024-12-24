package kz.zhanayev.ecommerce.specifications.impl;

import kz.zhanayev.ecommerce.models.Product;
import kz.zhanayev.ecommerce.specifications.ProductSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductSpecificationImpl implements ProductSpecifications {

    @Override
    public Specification<Product> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    @Override
    public Specification<Product> hasCategory(String categoryName) {
        return (root, query, criteriaBuilder) ->
                categoryName == null ? null : criteriaBuilder.equal(root.join("category").get("name"), categoryName);
    }

    @Override
    public Specification<Product> hasPriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) return null;
            if (minPrice == null) return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            if (maxPrice == null) return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
        };
    }

    @Override
    public Specification<Product> isInStock(Boolean inStock) {
        return (root, query, criteriaBuilder) ->
                inStock == null ? null : criteriaBuilder.greaterThan(root.get("stock"), 0);
    }
}
