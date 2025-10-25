package net.javaguides.springboot.response;

import lombok.Data;

@Data
public class RefreshTokenResponse {
    private Object data;
    private String message;
    private int status;
    
    @Data
    public static class TokenData {
        private String accessToken;
        private String refreshToken;
        private String tokenType = "Bearer";
        private long expiresIn; // Thời gian hết hạn của access token (giây)
        
        public TokenData(String accessToken, String refreshToken, long expiresIn) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.expiresIn = expiresIn;
        }
    }
}
