package kz.zhanayev.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class  RegisterUserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
}