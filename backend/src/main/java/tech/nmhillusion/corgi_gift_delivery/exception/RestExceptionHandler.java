package tech.nmhillusion.corgi_gift_delivery.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tech.nmhillusion.n2mix.exception.ApiResponseException;
import tech.nmhillusion.n2mix.exception.AppRuntimeException;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-26
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {AppRuntimeException.class})
    protected ResponseEntity<Object> handleAppRuntimeException(AppRuntimeException ex, WebRequest request) {

        final String bodyOfResponse = "Error: %s".formatted(ex.getMessage());

        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request
        );
    }

    @ExceptionHandler(value = {ApiResponseException.class})
    protected ResponseEntity<Object> handleAppRuntimeException(ApiResponseException ex, WebRequest request) {

        final String bodyOfResponse = "API Error: %s".formatted(ex.getMessage());

        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request
        );
    }
}
