package kz.zhanayev.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
    private Long id;
    private Long orderId;
    private String paymentMethod;
    private String status;
    private String transactionId;
}
