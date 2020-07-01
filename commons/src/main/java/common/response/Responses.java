package common.response;

import common.types.BaseResponse;
import common.types.ErrorResponse;
import common.types.ValidationErrorResponse;
import common.validation.ValidationError;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@UtilityClass
public final class Responses {
    public static BaseResponse success(String message) {
        return new BaseResponse(true, HttpStatus.OK, message);
    }

    public static ErrorResponse error(int statusCode, String message) {
        return new ErrorResponse(statusCode, message);
    }

    public static ErrorResponse error(HttpStatus status, String message) {
        return new ErrorResponse(status, message);
    }

    public static ErrorResponse error(HttpStatus status, Throwable ex) {
        return new ErrorResponse(status, ex);
    }

    public static ErrorResponse error(HttpStatus status, String message, Throwable ex) {
        return new ErrorResponse(status, message, ex);
    }

    public static ValidationErrorResponse validation(List<ValidationError> errors, String message) {
        return new ValidationErrorResponse(message, errors);
    }

    public static ValidationErrorResponse validation(ValidationError error, String message) {
        return new ValidationErrorResponse(message, new ValidationError[]{error});
    }

    public static ResponseEntity<BaseResponse> successEntity(String message) {
        BaseResponse response = success(message);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    public static <T extends BaseResponse> ResponseEntity<T> responseEntity(T response) {
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
