package kz.zhanayev.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO для категории продуктов")
public class CategoryDTO {

    @Schema(description = "Уникальный идентификатор категории", example = "1")
    private Long id;

    @Schema(description = "Название категории", example = "Электроника")
    private String name;

    @Schema(description = "Описание категории", example = "Все электронные устройства, включая микрочипов и плат")
    private String description;
}
