package net.javaguides.springboot.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseResponse<T> {
    private T data;
    private String message;
    private int status;
}
