package kz.zhanayev.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {

    @Schema(description = "Уникальный идентификатор платежа", example = "1")
    private Long id;

    @Schema(description = "Идентификатор заказа, к которому относится платеж", example = "101")
    private Long orderId;

    @Schema(description = "Метод оплаты (например, 'Карта', 'Кошелек' и т.д.)", example = "Карта")
    private String paymentMethod;

    @Schema(description = "Статус платежа (например, 'Успешно', 'Неудача')", example = "Успешно")
    private String status;

    @Schema(description = "Уникальный идентификатор транзакции платежа", example = "TX12345")
    private String transactionId;
}
