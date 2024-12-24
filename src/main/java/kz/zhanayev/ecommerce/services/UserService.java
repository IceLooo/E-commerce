package kz.zhanayev.ecommerce.services;


import kz.zhanayev.ecommerce.dto.LoginRequestDTO;
import kz.zhanayev.ecommerce.dto.RegisterUserDTO;
import kz.zhanayev.ecommerce.models.User;

public interface UserService {

     User saveUser(User user);
     User getUserById(Long id);
     void registerUser(RegisterUserDTO registerUserDTO);
     String authenticateUser(LoginRequestDTO loginRequestDTO);
     void createAdmin(String email, String password, String firstName, String lastName, String phoneNumber);
     User getUserByUsername(String username);
}