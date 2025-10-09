package net.javaguides.springboot.response;

import lombok.AllArgsConstructor;
import lombok.Data;

public class RegisterResponse extends BaseResponse<RegisterResponse.RegisterData> {

    @Data
    @AllArgsConstructor
    public static class RegisterData {
        private int userId;
    }
}
