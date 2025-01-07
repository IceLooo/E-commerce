package kz.zhanayev.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductDTO {

    @Schema(description = "Уникальный идентификатор товара", example = "1")
    private Long id;

    @Schema(description = "Название товара", example = "Bosch Drill")
    private String name;

    @Schema(description = "Описание товара", example = "Современный дрель с 2 моторчиками")
    private String description;

    @Schema(description = "Цена товара", example = "499.99")
    private BigDecimal price;

    @Schema(description = "Количество товара на складе", example = "100")
    private int stock;

    @Schema(description = "Вес товара в килограммах", example = "0.45")
    private Double weight;

    @Schema(description = "Идентификатор категории товара", example = "2")
    private Long categoryId;

    @Schema(description = "Идентификатор бренда товара", example = "5")
    private Long brandId;

    @Schema(description = "Список характеристик товара")
    private List<FeatureDTO> features;

    @Schema(description = "URL изображения товара", example = "https://example.com/product-image.jpg")
    private String imageUrl;
}
