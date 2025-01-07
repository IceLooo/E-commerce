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
    @Column(name = "postal_code")
    private String postalCode;

    @NotBlank
    @Column(name = "entrance")
    private String entrance; // Подъезд

    @NotBlank
    @Column(name = "apartment_office")
    private String apartmentOffice; // Квартира/Офис

    @NotBlank
    @Column(name = "courier_comments")
    private String courierComments; // Комментарии к курьеру

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
