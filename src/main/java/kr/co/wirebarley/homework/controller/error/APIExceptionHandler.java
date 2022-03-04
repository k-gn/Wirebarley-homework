package kr.co.wirebarley.homework.controller.error;

import kr.co.wirebarley.homework.constant.ErrorCode;
import kr.co.wirebarley.homework.dto.APIErrorResponse;
import kr.co.wirebarley.homework.exception.GeneralException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<Object> generalException(GeneralException e) {
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.isClientSideError() ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
        return handleExceptionResponse(e, errorCode, HttpHeaders.EMPTY, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodValidException(MethodArgumentNotValidException e) {
        return handleExceptionResponse(e, ErrorCode.VALIDATION_ERROR, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception e) {
        return handleExceptionResponse(e, ErrorCode.INTERNAL_ERROR, HttpHeaders.EMPTY, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> handleExceptionResponse(Exception e, ErrorCode errorCode, HttpHeaders headers, HttpStatus status) {
        return new ResponseEntity<>(APIErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(e)), headers, status);
    }
}
