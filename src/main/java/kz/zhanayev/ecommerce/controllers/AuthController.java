package kz.zhanayev.ecommerce.controllers;

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
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        userService.registerUser(registerUserDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Вы успешно зарегались, поздравляю.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
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

//    @GetMapping("/confirm")
//    public ResponseEntity<String> confirmUser(@RequestParam("token") String token) {
//        String username = jwtUtil.extractUsername(token); // Извлекаем username из токена
//        if (!jwtUtil.validateToken(token, username)) {    // Передаём токен и username для валидации
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
//        }
//        userService.confirmUser(token);
//        return ResponseEntity.ok("Регистрация подтверждена, теперь вы можете войти в систему.");
//    }


    // DTO для JWT ответа
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
