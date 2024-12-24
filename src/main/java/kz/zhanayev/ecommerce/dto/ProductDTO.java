package kz.zhanayev.ecommerce.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private Double weight;
    private Long categoryId;
    private Long brandId;
    private List<FeatureDTO> features;
    private String imageUrl; // Добавляем imageUrl в DTO
}
