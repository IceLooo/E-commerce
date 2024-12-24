package kz.zhanayev.ecommerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Min(1)
    private int quantity;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;
}
