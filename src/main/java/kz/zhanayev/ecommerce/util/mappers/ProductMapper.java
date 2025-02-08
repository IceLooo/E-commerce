package kz.zhanayev.ecommerce.util.mappers;

import kz.zhanayev.ecommerce.dto.FeatureDTO;
import kz.zhanayev.ecommerce.dto.ProductDTO;
import kz.zhanayev.ecommerce.models.Brand;
import kz.zhanayev.ecommerce.models.Category;
import kz.zhanayev.ecommerce.models.Feature;
import kz.zhanayev.ecommerce.models.Product;

import java.util.List;
import java.util.stream.Collectors;

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

        // ✅ Теперь добавляем `features` в `ProductDTO`
        if (product.getFeatures() != null) {
            productDTO.setFeatures(product.getFeatures().stream()
                    .map(feature -> new FeatureDTO(feature.getId(), feature.getName(), feature.getValue(), feature.getProduct().getId()))
                    .collect(Collectors.toList()));
        } else {
            productDTO.setFeatures(List.of()); // Возвращаем пустой список вместо null
        }

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

        // ✅ Теперь добавляем `features` в `Product`
        if (productDTO.getFeatures() != null) {
            List<Feature> features = productDTO.getFeatures().stream()
                    .map(dto -> {
                        Feature feature = new Feature();
                        feature.setName(dto.getName());
                        feature.setValue(dto.getValue());
                        feature.setProduct(product);
                        return feature;
                    }).collect(Collectors.toList());
            product.setFeatures(features);
        }

        product.setCategory(category);
        product.setBrand(brand);
        return product;
    }
}
