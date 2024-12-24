package kz.zhanayev.ecommerce.facade;

import kz.zhanayev.ecommerce.dto.FeatureDTO;
import kz.zhanayev.ecommerce.dto.ProductDTO;
import kz.zhanayev.ecommerce.models.Feature;
import kz.zhanayev.ecommerce.models.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductFacade {

    public ProductDTO productToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStock(product.getStock());
        productDTO.setWeight(product.getWeight());
        productDTO.setBrandId(product.getBrand().getId());

        if (product.getCategory() != null) {
            productDTO.setCategoryId(product.getCategory().getId());
        }

        productDTO.setImageUrl(product.getImageUrl());
        List<FeatureDTO> features = product.getFeatures().stream().map(feature -> {
            FeatureDTO dto = new FeatureDTO();
            dto.setId(feature.getId());
            dto.setName(feature.getName());
            dto.setValue(feature.getValue());
            return dto;
        }).toList();

        productDTO.setFeatures(features);
        return productDTO;
    }

    public Product productDTOToProduct(ProductDTO productDTO) {
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
        product.setFeatures(features);
        return product;
    }
}
