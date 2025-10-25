package net.javaguides.springboot.controller;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.dto.LoginRequest;
import net.javaguides.springboot.dto.RefreshTokenRequest;
import net.javaguides.springboot.dto.RegisterRequest;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.response.LoginResponse;
import net.javaguides.springboot.response.RefreshTokenResponse;
import net.javaguides.springboot.response.RegisterResponse;
import net.javaguides.springboot.security.JwtUtil;
import net.javaguides.springboot.service.RefreshTokenService;
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
    
    @Autowired
    private RefreshTokenService refreshTokenService;

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
            
            // Tạo cặp access token và refresh token
            String[] tokens = refreshTokenService.generateTokenPair(user.getId(), user.getEmail());
            String accessToken = tokens[0];
            String refreshToken = tokens[1];
            
            // Tạo response với cả access token và refresh token
            LoginResponse.AuthData authData = new LoginResponse.AuthData(accessToken, refreshToken);
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
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        try {
            // Xác thực refresh token
            if (!refreshTokenService.validateRefreshToken(request.getRefreshToken())) {
                RefreshTokenResponse response = new RefreshTokenResponse();
                response.setData(null);
                response.setMessage("Invalid or expired refresh token");
                response.setStatus(Constants.UNAUTHORIZED);
                return ResponseEntity.status(Constants.UNAUTHORIZED).body(response);
            }
            
            // Lấy userId từ refresh token
            Long userId = refreshTokenService.getUserIdFromRefreshToken(request.getRefreshToken());
            if (userId == null) {
                RefreshTokenResponse response = new RefreshTokenResponse();
                response.setData(null);
                response.setMessage("Invalid refresh token");
                response.setStatus(Constants.UNAUTHORIZED);
                return ResponseEntity.status(Constants.UNAUTHORIZED).body(response);
            }
            
            // Lấy thông tin user
            User user = userService.getUserById(userId);
            if (user == null) {
                RefreshTokenResponse response = new RefreshTokenResponse();
                response.setData(null);
                response.setMessage("User not found");
                response.setStatus(Constants.NOT_FOUND);
                return ResponseEntity.status(Constants.NOT_FOUND).body(response);
            }
            
            // Tạo cặp token mới
            String[] tokens = refreshTokenService.generateTokenPair(userId, user.getEmail());
            String newAccessToken = tokens[0];
            String newRefreshToken = tokens[1];
            
            // Tính thời gian hết hạn của access token (15 phút = 900 giây)
            long expiresIn = 15 * 60; // 15 phút
            
            RefreshTokenResponse.TokenData tokenData = new RefreshTokenResponse.TokenData(
                newAccessToken, newRefreshToken, expiresIn
            );
            
            RefreshTokenResponse response = new RefreshTokenResponse();
            response.setData(tokenData);
            response.setMessage("Token refreshed successfully");
            response.setStatus(Constants.SUCCESS);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            RefreshTokenResponse response = new RefreshTokenResponse();
            response.setData(null);
            response.setMessage("Error refreshing token: " + e.getMessage());
            response.setStatus(Constants.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(Constants.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
