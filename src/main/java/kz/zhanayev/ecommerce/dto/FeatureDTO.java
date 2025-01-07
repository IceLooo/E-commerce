package kz.zhanayev.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO для характеристики продукта")
public class FeatureDTO {

    @Schema(description = "Уникальный идентификатор характеристики", example = "1")
    private Long id;

    @Schema(description = "Название характеристики", example = "Цвет")
    private String name;

    @Schema(description = "Значение характеристики", example = "Черный")
    private String value;

    @Schema(description = "Идентификатор продукта, к которому относится характеристика", example = "101")
    private Long productId;
}
