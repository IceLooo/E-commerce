package kz.zhanayev.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO для адреса доставки")
public class AddressDTO {

    @Schema(description = "Уникальный идентификатор адреса", example = "1")
    private Long id;

    @Schema(description = "Страна", example = "Казахстан")
    private String country;

    @Schema(description = "Регион или область", example = "Алматинская область")
    private String region;

    @Schema(description = "Город", example = "Алматы")
    private String city;

    @Schema(description = "Улица", example = "Абая")
    private String street;

    @Schema(description = "Почтовый индекс", example = "050000")
    private String postalCode;

    @Schema(description = "Подъезд", example = "3")
    private String entrance;

    @Schema(description = "Квартира или офис", example = "45")
    private String apartmentOffice;

    @Schema(description = "Комментарии к курьеру", example = "Позвоните за 10 минут до прибытия")
    private String courierComments;

    @Schema(description = "Идентификатор пользователя, связанного с этим адресом", example = "123")
    private Long userId;
}
