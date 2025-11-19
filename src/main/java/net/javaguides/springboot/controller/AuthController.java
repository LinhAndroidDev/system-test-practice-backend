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
import org.antlr.v4.runtime.misc.Pair;
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
            Pair<Integer, String> r = userService.register(request);
            RegisterResponse response = getRegisterResponse(r.a, r.b);
            return ResponseEntity.ok(response);
        } catch (HttpClientErrorException e) {
            RegisterResponse response = new RegisterResponse();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setStatus(e.getStatusCode().value());
            return ResponseEntity.badRequest().body(response);
        }
    }

    private static RegisterResponse getRegisterResponse(int result, String message) {
        RegisterResponse response = new RegisterResponse();
        int status;
        if (result == 0) {
            status = Constants.DATA_CONFLICT;
        } else {
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
            Pair<User, String> r = userService.login(request.getEmail(), request.getPassword());
            User user = r.a;
            String message = r.b;
            LoginResponse response = new LoginResponse();
            if (user != null) {
                String token = jwtUtil.generateToken(user.getEmail());
                LoginResponse.AuthData authData = new LoginResponse.AuthData(token);
                LoginResponse.LoginData loginData = new LoginResponse.LoginData(user.getId().intValue(), user.getName(), user.getRole(), authData);
                response.setData(loginData);
                response.setMessage(message);
                response.setStatus(Constants.SUCCESS);
            } else {
                response.setData(null);
                response.setMessage(message);
                response.setStatus(Constants.FAILURE);
            }
            return ResponseEntity.ok(response);
        } catch (HttpClientErrorException e) {
            return handleFailLogin(e.getMessage(), e.getStatusCode().value());
        }
    }

    private ResponseEntity<?> handleFailLogin(String message, int statusCode) {
        LoginResponse response = new LoginResponse();
        response.setData(null);
        response.setMessage(message);
        response.setStatus(statusCode);
        return ResponseEntity.badRequest().body(response);
    }
}
