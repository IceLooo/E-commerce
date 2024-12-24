package kz.zhanayev.ecommerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String country;

    @NotBlank
    private String city;

    @NotBlank
    private String region;

    @NotBlank
    private String street;

    @Pattern(regexp = "^[0-9]{5}$", message = "Postal code must be 5 digits")
    private String postalCode;

    @NotBlank
    private String entrance; // Подъезд

    @NotBlank
    private String apartmentOffice; // Квартира/Офис

    @NotBlank
    private String courierComments; // Комментарии к курьеру

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
