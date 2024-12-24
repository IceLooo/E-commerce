package kz.zhanayev.ecommerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart", fetch = FetchType.LAZY)
    private List<CartItem> cartItems = new ArrayList<>();

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal totalPrice;
}
