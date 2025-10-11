package net.javaguides.springboot.response;

import lombok.AllArgsConstructor;
import lombok.Data;

public class LoginResponse extends BaseResponse<LoginResponse.LoginData>{

    @Data
    @AllArgsConstructor
    public static class LoginData {
        private int userId;
        private String username;
        private int role;
        private AuthData auth;
    }

    @Data
    @AllArgsConstructor
    public static class AuthData {
        private String accessToken;
    }
}
