package common.exception;

import common.types.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ErrorResponseException extends RuntimeException {

    private final ErrorResponse errorResponse;

}

