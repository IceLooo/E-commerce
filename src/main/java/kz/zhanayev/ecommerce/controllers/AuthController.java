package kz.zhanayev.ecommerce.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import kz.zhanayev.ecommerce.dto.LoginRequestDTO;
import kz.zhanayev.ecommerce.dto.RegisterUserDTO;
import kz.zhanayev.ecommerce.services.UserService;
import kz.zhanayev.ecommerce.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "API для аутентификации и регистрации пользователей")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    @Operation(
            summary = "Регистрация пользователя",
            description = "Регистрирует нового пользователя в системе",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(example = "{ \"message\": \"Вы успешно зарегались, поздравляю.\" }"))
                    ),
                    @ApiResponse(responseCode = "400", description = "Ошибка в данных запроса")
            }
    )
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        userService.registerUser(registerUserDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Вы успешно зарегались, поздравляю.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Вход пользователя",
            description = "Аутентификация пользователя и генерация JWT токена",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный вход и получение JWT токена",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JwtResponse.class))
                    ),
                    @ApiResponse(responseCode = "401", description = "Неверные учетные данные")
            }
    )
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(),
                        loginRequestDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Генерация JWT
        String token = jwtUtil.generateToken(authentication);

        // Возврат токена в обёртке
        return ResponseEntity.ok(new JwtResponse(token));
    }

    // DTO для JWT ответа
    @Schema(name = "JWT Response", description = "Объект ответа с токеном JWT")
    static class JwtResponse {
        private String token;

        public JwtResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
