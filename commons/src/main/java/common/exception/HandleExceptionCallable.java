package common.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import common.util.MessageKeys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.client.HttpClientErrorException;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;

@Getter
@AllArgsConstructor
public enum HandleExceptionCallable {

    EMPTY_RESULT_DATA(EmptyResultDataAccessException.class, ConstantsException.handleEmptyResultDataFailure, MessageKeys.NOT_FOUND),
    DATABASE_CONNECTION(DataAccessResourceFailureException.class, ConstantsException.handleDataBaseConnectionFailures, MessageKeys.ERROR_DB_CONNECTION),
    CONSTRAINT_VIOLATION(ConstraintViolationException.class, ConstantsException.handleConstraintViolationFailure, MessageKeys.ERROR_DB_CONSTRAINTS),
    DATA_INTEGRITY_VIOLATION(DataIntegrityViolationException.class, ConstantsException.handleDataIntegrityViolationFailure, MessageKeys.ERROR_DB_CONSTRAINTS),
    CLIENT_ERROR(HttpClientErrorException.class, ConstantsException.handleClientErrorFailure, null),
    NOT_FOUND(NotFoundException.class, ConstantsException.handleNotFoundFailure, MessageKeys.ERROR_DB_CONSTRAINTS),
    NOT_FOUND_OBJECT(NotFoundObjectException.class, ConstantsException.handleNotFoundObjectFailure, MessageKeys.NOT_FOUND_OBJECT),
    NOT_AUTHORIZED(NotAuthorizedException.class, ConstantsException.handleNotAuthorizedFailure, null),
    CONVERSION(ConversionFailedException.class, ConstantsException.handleConversionFailure, MessageKeys.ERROR_UNEXPECTED),
    CONVERTER_NOT_FOUND(ConverterNotFoundException.class, ConstantsException.handleConverterNotFoundFailure, MessageKeys.ERROR_CONVERTER_NOT_FOUND),
    INVALID_ENUM_VALUE(InvalidEnumValueException.class, ConstantsException.handleInvalidEnumValueFailure, MessageKeys.VALIDATION_ENUM_INVALID),
    VALIDATION(ValidationException.class, ConstantsException.handleValidationFailure, MessageKeys.VALIDATION_MESSAGE),
    MISC(Exception.class, ConstantsException.handleMiscFailures, MessageKeys.ERROR_UNEXPECTED);

    private Class<? extends Exception> exception;
    private ExceptionCallable exceptionCallable;
    private String messageKey;

    @JsonValue
    public String getValue() {
        return name();
    }

    @JsonCreator
    public static HandleExceptionCallable fromValue(Class<? extends Exception> value) {
        for (HandleExceptionCallable p : values()) {
            if (p.getException().equals(value)) {
                return p;
            }
        }
        return null;
    }

}
