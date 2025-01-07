package kz.zhanayev.ecommerce.util.mappers;

import kz.zhanayev.ecommerce.dto.ProductDTO;
import kz.zhanayev.ecommerce.models.Brand;
import kz.zhanayev.ecommerce.models.Category;
import kz.zhanayev.ecommerce.models.Feature;
import kz.zhanayev.ecommerce.models.Product;

import java.util.List;

public class ProductMapper {
    public static ProductDTO toDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStock(product.getStock());
        productDTO.setWeight(product.getWeight());
        productDTO.setImageUrl(product.getImageUrl());
        List<Feature> features = productDTO.getFeatures().stream().map(dto -> {
            Feature feature = new Feature();
            feature.setName(dto.getName());
            feature.setValue(dto.getValue());
            feature.setProduct(product);
            return feature;
        }).toList();
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setBrandId(product.getBrand().getId());
        return productDTO;
    }

    public static Product toEntity(ProductDTO productDTO, Category category, Brand brand) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setWeight(productDTO.getWeight());
        product.setImageUrl(productDTO.getImageUrl());
        List<Feature> features = productDTO.getFeatures().stream().map(dto -> {
            Feature feature = new Feature();
            feature.setName(dto.getName());
            feature.setValue(dto.getValue());
            feature.setProduct(product);
            return feature;
        }).toList();
        product.setCategory(category);
        product.setBrand(brand);
        return product;
    }
}
