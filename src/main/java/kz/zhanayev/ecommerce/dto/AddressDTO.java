package kz.zhanayev.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    private Long id;
    private String country;
    private String region;
    private String city;
    private String street;
    private String postalCode;
    private String entrance; // Подъезд
    private String apartmentOffice; // Квартира/Офис
    private String courierComments; // Комментарии к курьеру
}
