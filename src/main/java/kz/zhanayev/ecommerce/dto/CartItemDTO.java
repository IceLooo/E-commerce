package kz.zhanayev.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(description = "DTO для элемента корзины пользователя")
public class CartItemDTO {

    @Schema(description = "Уникальный идентификатор элемента корзины", example = "1")
    private Long id;

    @Schema(description = "Идентификатор продукта, связанного с этим элементом", example = "101")
    private Long productId;

    @Schema(description = "Название продукта", example = "Ноутбук Bosch Drill")
    private String productName;

    @Schema(description = "Количество продукта в корзине", example = "2")
    private int quantity;

    @Schema(description = "Цена продукта", example = "750.50")
    private BigDecimal price;
}
