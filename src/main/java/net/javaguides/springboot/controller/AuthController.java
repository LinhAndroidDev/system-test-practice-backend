package net.javaguides.springboot.controller;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.dto.LoginRequest;
import net.javaguides.springboot.dto.RegisterRequest;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.response.LoginResponse;
import net.javaguides.springboot.response.RegisterResponse;
import net.javaguides.springboot.security.JwtUtil;
import net.javaguides.springboot.service.UserService;
import net.javaguides.springboot.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        try {
            int result = userService.register(request);
            RegisterResponse response = getRegisterResponse(result);
            return ResponseEntity.ok(response);
        } catch (HttpClientErrorException e) {
            RegisterResponse response = new RegisterResponse();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setStatus(e.getStatusCode().value());
            return ResponseEntity.badRequest().body(response);
        }
    }

    private static RegisterResponse getRegisterResponse(int result) {
        RegisterResponse response = new RegisterResponse();
        String message;
        int status;
        if (result == 0) {
            message = "Email already exists!";
            status = Constants.DATA_CONFLICT;
        } else {
            message = "Registration successful!";
            status = Constants.SUCCESS;
        }
        response.setData(new RegisterResponse.RegisterData(result));
        response.setMessage(message);
        response.setStatus(status);
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        try {
            User user = userService.login(request.getEmail(), request.getPassword());
            String token = jwtUtil.generateToken(user.getEmail());
            LoginResponse.AuthData authData = new LoginResponse.AuthData(token);
            LoginResponse.LoginData loginData = new LoginResponse.LoginData(user.getId().intValue(), user.getName(), user.getRole(), authData);
            LoginResponse response = new LoginResponse();
            response.setData(loginData);
            response.setMessage("Login successful!");
            response.setStatus(Constants.SUCCESS);
            return ResponseEntity.ok(response);
        } catch (HttpClientErrorException e) {
            LoginResponse response = new LoginResponse();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setStatus(e.getStatusCode().value());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
