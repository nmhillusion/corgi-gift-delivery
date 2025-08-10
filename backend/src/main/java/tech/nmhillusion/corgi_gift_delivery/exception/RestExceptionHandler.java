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
import tech.nmhillusion.n2mix.model.ApiErrorResponse;

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

        final ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST
                , ex.getClass().getName()
                , bodyOfResponse
        );

        return handleExceptionInternal(ex, apiErrorResponse.toString(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request
        );
    }

    @ExceptionHandler(value = {ApiResponseException.class})
    protected ResponseEntity<Object> handleApiResponseException(ApiResponseException ex, WebRequest request) {

        final String bodyOfResponse = "API Error: %s".formatted(ex.getMessage());

        final ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST
                , ex.getClass().getName()
                , bodyOfResponse
        );

        return handleExceptionInternal(ex, apiErrorResponse.toString(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request
        );
    }
}
