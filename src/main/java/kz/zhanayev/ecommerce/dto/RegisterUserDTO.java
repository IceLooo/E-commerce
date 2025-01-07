package kz.zhanayev.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDTO {

    @Schema(description = "Имя пользователя", example = "Иван")
    private String firstName;

    @Schema(description = "Фамилия пользователя", example = "Иванов")
    private String lastName;

    @Schema(description = "Электронная почта пользователя", example = "ivan.ivanov@example.com")
    private String email;

    @Schema(description = "Пароль пользователя", example = "securePassword123")
    private String password;

    @Schema(description = "Номер телефона пользователя", example = "+77011234567")
    private String phoneNumber;
}
