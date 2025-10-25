package net.javaguides.springboot.service;

import net.javaguides.springboot.entity.RefreshToken;
import net.javaguides.springboot.repository.RefreshTokenRepository;
import net.javaguides.springboot.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    // Tạo refresh token mới
    @Transactional
    public String createRefreshToken(Long userId) {
        // Xóa tất cả refresh token cũ của user
        refreshTokenRepository.deleteByUserId(userId);
        
        // Tạo refresh token mới
        String token = UUID.randomUUID().toString();
        LocalDateTime expiresAt = LocalDateTime.now().plusDays(7); // 7 ngày
        
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setUserId(userId);
        refreshToken.setExpiresAt(expiresAt);
        refreshToken.setCreatedAt(LocalDateTime.now());
        refreshToken.setRevoked(false);
        
        refreshTokenRepository.save(refreshToken);
        
        return token;
    }
    
    // Xác thực refresh token
    public boolean validateRefreshToken(String token) {
        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByToken(token);
        
        if (refreshTokenOpt.isEmpty()) {
            return false;
        }
        
        RefreshToken refreshToken = refreshTokenOpt.get();
        
        // Kiểm tra token đã bị revoke chưa
        if (refreshToken.isRevoked()) {
            return false;
        }
        
        // Kiểm tra token đã hết hạn chưa
        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            // Xóa token hết hạn
            refreshTokenRepository.delete(refreshToken);
            return false;
        }
        
        return true;
    }
    
    // Lấy userId từ refresh token
    public Long getUserIdFromRefreshToken(String token) {
        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByToken(token);
        return refreshTokenOpt.map(RefreshToken::getUserId).orElse(null);
    }
    
    // Revoke refresh token
    @Transactional
    public void revokeRefreshToken(String token) {
        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByToken(token);
        if (refreshTokenOpt.isPresent()) {
            RefreshToken refreshToken = refreshTokenOpt.get();
            refreshToken.setRevoked(true);
            refreshTokenRepository.save(refreshToken);
        }
    }
    
    // Revoke tất cả refresh token của user
    @Transactional
    public void revokeAllUserRefreshTokens(Long userId) {
        refreshTokenRepository.revokeByUserId(userId);
    }
    
    // Xóa tất cả refresh token hết hạn
    @Transactional
    public void deleteExpiredTokens() {
        refreshTokenRepository.deleteExpiredTokens(LocalDateTime.now());
    }
    
    // Tạo cặp access token và refresh token mới
    @Transactional
    public String[] generateTokenPair(Long userId, String email) {
        // Tạo refresh token mới
        String refreshToken = createRefreshToken(userId);
        
        // Tạo access token mới
        String accessToken = jwtUtil.generateAccessToken(email);
        
        return new String[]{accessToken, refreshToken};
    }
}
