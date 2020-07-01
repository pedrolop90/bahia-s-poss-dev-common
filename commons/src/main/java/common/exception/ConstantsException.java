package common.exception;

import common.response.Responses;
import common.types.ErrorResponse;
import common.types.ValidationErrorResponse;
import common.util.UtilJson;
import common.validation.ValidationError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

public interface ConstantsException {

    ExceptionCallable handleEmptyResultDataFailure = (Exception ex, WebRequest request, String message) -> {
        ErrorResponse error = Responses.error(HttpStatus.NOT_FOUND, message, ex);
        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
    };
    ExceptionCallable handleDataBaseConnectionFailures = (Exception ex, WebRequest request, String message) -> {
        ErrorResponse error = Responses.error(HttpStatus.INTERNAL_SERVER_ERROR, message, ex);
        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
    };
    ExceptionCallable handleConstraintViolationFailure = (Exception ex, WebRequest request, String message) -> {
        ErrorResponse error = Responses.error(HttpStatus.INTERNAL_SERVER_ERROR, message, ex);
        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
    };
    ExceptionCallable handleDataIntegrityViolationFailure = (Exception ex, WebRequest request, String message) -> {
        ErrorResponse error = Responses.error(HttpStatus.INTERNAL_SERVER_ERROR, message, ex);
        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
    };
    ExceptionCallable handleClientErrorFailure = (Exception ex, WebRequest request, String message) -> {
        HttpClientErrorException httpClientErrorException = (HttpClientErrorException) ex;
        String bodyString = httpClientErrorException.getResponseBodyAsString();
        ErrorResponse error = (ErrorResponse) UtilJson.toObject(bodyString, ErrorResponse.class);
        if (error == null) {
            error = Responses.error(httpClientErrorException.getStatusCode(), httpClientErrorException.getResponseBodyAsString());
        }
        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
    };
    ExceptionCallable handleNotFoundFailure = (Exception ex, WebRequest request, String message) -> {
        ErrorResponse error = Responses.error(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
    };
    ExceptionCallable handleNotFoundObjectFailure = (Exception ex, WebRequest request, String message) -> {
        ErrorResponse error = Responses.error(HttpStatus.NOT_FOUND, message);
        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
    };
    ExceptionCallable handleNotAuthorizedFailure = (Exception ex, WebRequest request, String message) -> {
        ErrorResponse error = Responses.error(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
    };
    ExceptionCallable handleConversionFailure = (Exception ex, WebRequest request, String message) -> {
        ErrorResponse error = Responses.error(HttpStatus.BAD_REQUEST, message, ex);
        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
    };
    ExceptionCallable handleConverterNotFoundFailure = (Exception ex, WebRequest request, String message) -> {
        ErrorResponse error = Responses.error(HttpStatus.INTERNAL_SERVER_ERROR, message, ex);
        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
    };
    ExceptionCallable handleInvalidEnumValueFailure = (Exception ex, WebRequest request, String message) -> {
        InvalidEnumValueException invalidEnumValueException = (InvalidEnumValueException) ex;
        ValidationError error = new ValidationError(invalidEnumValueException.getField(), message);
        ValidationErrorResponse response = Responses.validation(error, "");
        return new ResponseEntity<>(error, new HttpHeaders(), response.getStatus());
    };
    ExceptionCallable handleValidationFailure = (Exception ex, WebRequest request, String message) -> {
        ValidationException validationException = (ValidationException) ex;
        ValidationErrorResponse response = Responses.validation(validationException.getErrors(), message);
        return new ResponseEntity<>(response, new HttpHeaders(), response.getStatus());
    };
    ExceptionCallable handleMiscFailures = (Exception ex, WebRequest request, String message) -> {
        ErrorResponse error = Responses.error(HttpStatus.BAD_REQUEST, message, ex);
        return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
    };


}
