package kz.zhanayev.ecommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO {

    @Schema(description = "Уникальный идентификатор роли", example = "1")
    private Long id;

    @Schema(description = "Название роли", example = "ADMIN")
    private String name;
}
