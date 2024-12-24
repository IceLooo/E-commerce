package kz.zhanayev.ecommerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @NotBlank(message = "Payment method cannot be blank")
    private String paymentMethod;

    @NotBlank(message = "Payment status cannot be blank")
    private String status;

    @NotNull
    @Pattern(regexp = "^[a-fA-F0-9-]{36}$", message = "Transaction ID must be a valid UUID")
    private String transactionId;
}
