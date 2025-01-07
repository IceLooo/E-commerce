package kz.zhanayev.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO для бренда, используемого в запросах и ответах API")
public class BrandDTO {

    @Schema(description = "Уникальный идентификатор бренда", example = "1")
    private Long id;

    @Schema(description = "Название бренда", example = "Bosch")
    private String name;

    @Schema(description = "Описание бренда", example = "Производитель высококачественных электронных устройств")
    private String description;
}
