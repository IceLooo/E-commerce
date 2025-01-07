package kz.zhanayev.ecommerce.dto;

import kz.zhanayev.ecommerce.models.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDTO {

    @Schema(description = "Идентификатор заказа", example = "1")
    private Long id;

    @Schema(description = "Идентификатор пользователя, который сделал заказ", example = "123")
    private Long userId;

    @Schema(description = "Список товаров в заказе")
    private List<OrderItemDTO> orderItems;

    @Schema(description = "Общая стоимость заказа")
    private BigDecimal totalPrice;

    @Schema(description = "Адрес доставки для заказа")
    private AddressDTO address;

    @Schema(description = "Дата и время создания заказа", example = "2025-01-06T10:15:30")
    private LocalDateTime createdAt;

    @Schema(description = "Статус заказа", example = "PENDING")
    private OrderStatus status;

    @Schema(description = "Идентификатор адреса для доставки", example = "10")
    private Long addressId;
}
