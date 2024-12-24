package kz.zhanayev.ecommerce.facade;

import kz.zhanayev.ecommerce.dto.UserDTO;
import kz.zhanayev.ecommerce.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {
    public UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        return userDTO;
    }
}
