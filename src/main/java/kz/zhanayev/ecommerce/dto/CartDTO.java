package kz.zhanayev.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Schema(description = "DTO для корзины пользователя")
public class CartDTO {

    @Schema(description = "Уникальный идентификатор корзины", example = "1")
    private Long id;

    @Schema(description = "Идентификатор пользователя, связанного с корзиной", example = "1001")
    private Long userId;

    @Schema(description = "Список элементов корзины", implementation = CartItemDTO.class)
    private List<CartItemDTO> cartItems;

    @Schema(description = "Общая стоимость всех элементов в корзине", example = "1500.75")
    private BigDecimal totalPrice;
}
