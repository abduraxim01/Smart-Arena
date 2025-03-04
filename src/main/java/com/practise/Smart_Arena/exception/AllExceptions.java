package com.practise.Smart_Arena.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

public class AllExceptions {

    @Getter
    public static class IllegalArgumentException extends RuntimeException {
        private final HttpStatus status = HttpStatus.BAD_REQUEST;

        public IllegalArgumentException(String message) {
            super(message);
        }
    }

    @Getter
    public static class UsernameAlreadyTakenException extends RuntimeException {
        private final HttpStatus status = HttpStatus.CONFLICT;

        public UsernameAlreadyTakenException(String message) {
            super(message);
        }
    }

    @Getter
    public static class EntityNotFoundException extends RuntimeException {
        private final HttpStatus status = HttpStatus.NOT_FOUND;

        public EntityNotFoundException(String message) {
            super(message);
        }
    }

    @Getter
    public static class SignatureException extends RuntimeException {
        private final HttpStatus status = HttpStatus.UNAUTHORIZED;

        public SignatureException(String message) {
            super(message);
        }
    }

    @Getter
    public static class ExpiredJwtException extends RuntimeException {
        private final HttpStatus status = HttpStatus.BAD_GATEWAY;

        public ExpiredJwtException(String message) {
            super(message);
        }
    }

    @Getter
    public static class MalformedJwtException extends RuntimeException {
        private final HttpStatus status = HttpStatus.BAD_GATEWAY;

        public MalformedJwtException(String message) {
            super(message);
        }
    }

    @Getter
    public static class HttpResponseException extends RuntimeException {
        private final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        public HttpResponseException(String message) {
            super(message);
        }
    }

    @Getter
    public static class IOException extends RuntimeException {
        private final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        public IOException(String message) {
            super(message);
        }
    }

    @Getter
    public static class DataIntegrityViolationException extends RuntimeException {
        private final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        public DataIntegrityViolationException(String message) {
            super(message);
        }
    }

    @Getter
    public static class InternalServerError extends RuntimeException {
        private final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        public InternalServerError(String message) {
            super(message);
        }
    }
}