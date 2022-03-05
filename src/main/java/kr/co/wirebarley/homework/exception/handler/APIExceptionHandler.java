package kr.co.wirebarley.homework.exception.handler;

import kr.co.wirebarley.homework.constant.ErrorCode;
import kr.co.wirebarley.homework.dto.APIErrorResponse;
import kr.co.wirebarley.homework.exception.GeneralException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<?> general(GeneralException e) {
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.isClientSideError() ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
        return handleExceptionResponse(e, errorCode, HttpHeaders.EMPTY, status);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> ValidException(BindException e) {
        ObjectError objectError = e.getBindingResult().getAllErrors().stream().findFirst().get();
        if(objectError.getDefaultMessage().contains("NumberFormatException") && objectError.getCode().equals("typeMismatch")) {
            return handleExceptionResponse(e, ErrorCode.VALIDATION_ERROR, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, "송금액이 바르지 않습니다.");
        }else {
            return handleExceptionResponse(e, ErrorCode.VALIDATION_ERROR, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, objectError.getDefaultMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception e) {
        e.printStackTrace();
        return handleExceptionResponse(e, ErrorCode.INTERNAL_ERROR, HttpHeaders.EMPTY, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<?> handleExceptionResponse(Exception e, ErrorCode errorCode, HttpHeaders headers, HttpStatus status) {
        return new ResponseEntity<>(APIErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(e)), headers, status);
    }

    private ResponseEntity<?> handleExceptionResponse(Exception e, ErrorCode errorCode, HttpHeaders headers, HttpStatus status, String message) {
        return new ResponseEntity<>(APIErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(message)), headers, status);
    }
}
