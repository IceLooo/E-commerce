package kz.zhanayev.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO для запроса оплаты с использованием карты")
public class CardPaymentRequest {

    @NotNull
    @Pattern(regexp = "\\d{16}", message = "Card number must be 16 digits")
    @Schema(description = "Номер карты (16 цифр)", example = "1234567812345678")
    private String cardNumber;

    @NotNull
    @Pattern(regexp = "(0[1-9]|1[0-2])/[0-9]{2}", message = "Expiration date must be in MM/YY format")
    @Schema(description = "Срок действия карты в формате MM/YY", example = "12/25")
    private String expirationDate;

    @NotNull
    @Pattern(regexp = "\\d{3}", message = "CVC must be 3 digits")
    @Schema(description = "Код CVC (3 цифры)", example = "123")
    private String cvc;

    @NotNull
    @Schema(description = "Идентификатор заказа, связанного с оплатой", example = "1001")
    private Long orderId;
}
