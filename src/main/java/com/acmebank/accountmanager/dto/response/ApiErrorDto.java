package com.acmebank.accountmanager.dto.response;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@ToString
public class ApiErrorDto {

    private final HttpStatus status;
    private final LocalDateTime timestamp;
    private final String message;

    private ApiErrorDto(HttpStatus status, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }

    public static ApiErrorDto notFound() {
        return new ApiErrorDto(HttpStatus.NOT_FOUND, "The resource you are looking for is not found");
    }

    public static ApiErrorDto serverSideError(String errorMessage) {
        return new ApiErrorDto(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }

    public static ApiErrorDto serverSideError() {
        return new ApiErrorDto(HttpStatus.INTERNAL_SERVER_ERROR, "The service is temporary unavailable");
    }

    public static ApiErrorDto clientSideError(String errorMessage) {
        return new ApiErrorDto(HttpStatus.BAD_REQUEST, errorMessage);
    }

    public static ApiErrorDto clientSideError() {
        return new ApiErrorDto(HttpStatus.BAD_REQUEST, "Unable to process your request");
    }

    public static ApiErrorDto with(HttpStatus httpStatus, String message) {
        return new ApiErrorDto(httpStatus, message);
    }
}
