package kz.zhanayev.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemDTO {

    @Schema(description = "Идентификатор позиции в заказе", example = "1")
    private Long id;

    @Schema(description = "Идентификатор товара", example = "101")
    private Long productId;

    @Schema(description = "Название товара", example = "Дрель")
    private String productName;

    @Schema(description = "Количество товара в заказе", example = "2")
    private int quantity;

    @Schema(description = "Цена за единицу товара", example = "500.00")
    private BigDecimal price;
}
