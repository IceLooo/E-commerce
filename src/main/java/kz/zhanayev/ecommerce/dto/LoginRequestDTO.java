package kz.zhanayev.ecommerce.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    @Schema(description = "Адрес электронной почты пользователя", example = "user@example.com")
    private String email;

    @Schema(description = "Пароль пользователя", example = "password123")
    private String password;
}
