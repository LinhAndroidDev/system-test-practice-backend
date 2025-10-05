package net.javaguides.springboot.controller;

import net.javaguides.springboot.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public abstract class BaseController<R extends BaseResponse<T>, T> {
    private final Class<R> responseClass;

    protected BaseController(Class<R> responseClass) {
        this.responseClass = responseClass;
    }

    protected ResponseEntity<R> handleException(
            HttpClientErrorException e
    ) {
        try {
            R response = responseClass.getDeclaredConstructor().newInstance();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setStatus(e.getStatusCode().value());
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to create response instance", ex);
        }
    }

    protected ResponseEntity<R> handleSuccess(
            T data,
            String message,
            int status
    ) {
        try {
            R response = responseClass.getDeclaredConstructor().newInstance();
            response.setData(data);
            response.setMessage(message);
            response.setStatus(status);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create response instance", e);
        }
    }
}
