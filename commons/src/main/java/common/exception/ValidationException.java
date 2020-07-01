package common.exception;

import common.validation.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class ValidationException extends RuntimeException {
    private final List<ValidationError> errors;

    public ValidationException(ValidationError... errors) {
        this(Arrays.asList(errors));
    }

}

