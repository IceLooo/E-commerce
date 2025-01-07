package kz.zhanayev.ecommerce.services.impl;

import kz.zhanayev.ecommerce.dto.LoginRequestDTO;
import kz.zhanayev.ecommerce.dto.RegisterUserDTO;
import kz.zhanayev.ecommerce.exceptions.NotFoundException;
import kz.zhanayev.ecommerce.models.Role;
import kz.zhanayev.ecommerce.models.enums.RoleName;
import kz.zhanayev.ecommerce.models.User;
import kz.zhanayev.ecommerce.repositories.RoleRepository;
import kz.zhanayev.ecommerce.repositories.UserRepository;
import kz.zhanayev.ecommerce.services.UserService;
import kz.zhanayev.ecommerce.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository,
                           JwtUtil jwtUtil,
                           AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден с идентификатором: " + id));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден с именем: " + username));
    }

    @Override
    @Transactional
    public void registerUser(RegisterUserDTO registerUserDTO) {
        User user = new User();
        user.setFirstName(registerUserDTO.getFirstName());
        user.setLastName(registerUserDTO.getLastName());
        user.setEmail(registerUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        user.setPhoneNumber(registerUserDTO.getPhoneNumber());
        user.setEnabled(true);

        Role userRole = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new NotFoundException("Роль по умолчанию не найдена"));

        user.setRole(userRole);
        userRepository.save(user);
    }

    @Override
    public String authenticateUser(LoginRequestDTO loginRequestDTO) {
        // Аутентификация с использованием AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(),
                        loginRequestDTO.getPassword()
                )
        );

        // Генерация токена через JwtUtil
        return jwtUtil.generateToken(authentication);
    }

    @Override
    @Transactional
    public void createAdmin(String email, String password, String firstName, String lastName, String phoneNumber) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким адресом электронной почты уже существует.");
        }

        User admin = new User();
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setPhoneNumber(phoneNumber);
        admin.setEnabled(true);

        Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                .orElseThrow(() -> new NotFoundException("Роль администратора не найдена"));

        admin.setRole(adminRole);
        userRepository.save(admin);
    }
}
