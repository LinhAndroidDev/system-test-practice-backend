package net.javaguides.springboot.response;

import lombok.Data;

@Data
public abstract class BaseResponse<T> {
    private T data;
    private String message;
    private int status;
}
