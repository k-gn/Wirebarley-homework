package kr.co.wirebarley.homework.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 2xx
    OK(0, ErrorCategory.NORMAL, "Ok"),

    // 4xx error case
    BAD_REQUEST(10000, ErrorCategory.CLIENT_SIDE, "Bad request"),
    VALIDATION_ERROR(10001, ErrorCategory.CLIENT_SIDE, "Validation error"),

    // 5xx error case
    INTERNAL_ERROR(20000, ErrorCategory.SERVER_SIDE, "Internal error");

    private final Integer code;
    private final ErrorCategory errorCategory;
    private final String message;

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    public boolean isClientSideError() {
        return this.getErrorCategory() == ErrorCategory.CLIENT_SIDE;
    }

    public boolean isServerSideError() {
        return this.getErrorCategory() == ErrorCategory.SERVER_SIDE;
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", this.name(), this.getCode());
    }

    public enum ErrorCategory {
        NORMAL, CLIENT_SIDE, SERVER_SIDE
    }

}