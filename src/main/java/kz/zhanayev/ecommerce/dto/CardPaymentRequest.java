package kz.zhanayev.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardPaymentRequest {
    @NotNull
    @Pattern(regexp = "\\d{16}", message = "Card number must be 16 digits")
    private String cardNumber;

    @NotNull
    @Pattern(regexp = "(0[1-9]|1[0-2])/[0-9]{2}", message = "Expiration date must be in MM/YY format")
    private String expirationDate;

    @NotNull
    @Pattern(regexp = "\\d{3}", message = "CVC must be 3 digits")
    private String cvc;

    @NotNull
    private Long orderId;
}
