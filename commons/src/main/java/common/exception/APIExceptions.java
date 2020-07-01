package common.exception;

import common.types.ErrorResponse;
import common.validation.ValidationError;
import lombok.experimental.UtilityClass;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import java.util.List;

@UtilityClass
public final class APIExceptions {

    public static NotFoundException objetoNoEncontrado(Object id) {
        return new NotFoundObjectException(id);
    }

    public static NotFoundException objetoNoEncontrado(String message) {
        return new NotFoundException(message);
    }

    public static NotAuthorizedException noAutorizado(String message) {
        return new NotAuthorizedException(message, new Object(), (Object[]) null);
    }

    public static BadRequestException solicitudIncorrecta(String message) {
        return new BadRequestException(message);
    }

    public static ValidationException errorValidation(ValidationError... errors) {
        return new ValidationException(errors);
    }

    public static ValidationException errorValidation(List<ValidationError> errors) {
        return new ValidationException(errors);
    }

    public static ValidationException errorValidation(String message) {
        return new ValidationException(new ValidationError[]{new ValidationError(message)});
    }

    public static SoftDeleteEntityException softDeleteError(String message) {
        return new SoftDeleteEntityException(message);
    }

    public static ErrorResponseException errorResponse(ErrorResponse errorResponse) {
        return new ErrorResponseException(errorResponse);
    }
}
