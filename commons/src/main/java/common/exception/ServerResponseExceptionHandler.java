package common.exception;


import common.response.Responses;
import common.types.ErrorResponse;
import common.types.ValidationErrorResponse;
import common.util.MessageKeys;
import common.validation.ValidationError;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.NotSupportedException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@NoArgsConstructor
@Log4j2
public abstract class ServerResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({
            NotAuthorizedException.class,
            NotFoundException.class,
            NotFoundObjectException.class,
            HttpClientErrorException.class,
            HttpServerErrorException.class,
            ConversionFailedException.class,
            ConverterNotFoundException.class,
            InvalidEnumValueException.class,
            ValidationException.class,
            InvocationTargetException.class,
            IllegalArgumentException.class,
            BadRequestException.class,
            NotSupportedException.class,
            IllegalStateException.class,
            ErrorResponseException.class})
    public final ResponseEntity<Object> handleCommonWebFailures(Exception ex, WebRequest request) {
        HandleExceptionCallable handleExceptionCallable = HandleExceptionCallable.fromValue(ex.getClass());
        if (handleExceptionCallable != null) {
            String message = null;
            if (handleExceptionCallable.getMessageKey() != null) {
                message = this.getMessageSource().getMessage(handleExceptionCallable.getMessageKey(), null, LocaleContextHolder.getLocale());
            }
            return handleExceptionCallable.getExceptionCallable().callable(ex, request, message);
        }
        return this.handleMiscFailures(ex, request);
    }

    protected ResponseEntity<Object> handleInvalidEnumValueFailure(InvalidEnumValueException ex, WebRequest request) {
        String message = this.messageSource.getMessage(
                MessageKeys.VALIDATION_ENUM_INVALID, new Object[]{ex.getValue()}, LocaleContextHolder.getLocale());
        String msg = this.messageSource.getMessage(
                MessageKeys.VALIDATION_MESSAGE, null, LocaleContextHolder.getLocale());
        ValidationError error = new ValidationError(ex.getField(), message);
        ValidationErrorResponse response = Responses.validation(error, msg);
        return this.handleExceptionInternal(ex, response, new HttpHeaders(), response.getStatus(), request);
    }

    protected ResponseEntity<Object> handleMiscFailures(Exception ex, WebRequest request) {
        String message = this.messageSource.getMessage(
                MessageKeys.ERROR_UNEXPECTED, null, LocaleContextHolder.getLocale());
        ErrorResponse error = Responses.error(HttpStatus.BAD_REQUEST, message, ex);
        return this.handleExceptionInternal(ex, error, new HttpHeaders(), error.getStatus(), request);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ValidationError> errors = new ArrayList();
        BindingResult result = ex.getBindingResult();

        ValidationError error;
        for (Iterator var7 = result.getAllErrors().iterator(); var7.hasNext(); errors.add(error)) {
            ObjectError objectError = (ObjectError) var7.next();
            error = new ValidationError();
            error.setMessage(objectError.getDefaultMessage());
            if (objectError instanceof FieldError) {
                error.setField(((FieldError) objectError).getField());
            }
        }

        String message = this.messageSource.getMessage(
                MessageKeys.VALIDATION_MESSAGE, null, LocaleContextHolder.getLocale());
        return this.handleExceptionInternal(ex, Responses.validation(errors, message), headers, status, request);
    }

    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex.getRootCause() instanceof InvalidEnumValueException) {
            return this.handleInvalidEnumValueFailure((InvalidEnumValueException) ex.getRootCause(), request);
        }
        else {
            String message = this.messageSource.getMessage(
                    MessageKeys.VALIDATION_INVALID_JSON, null, LocaleContextHolder.getLocale());
            ErrorResponse error = Responses.error(HttpStatus.BAD_REQUEST, message);
            return this.handleExceptionInternal(ex, error, new HttpHeaders(), error.getStatus(), request);
        }
    }

    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        this.logger.debug(ex.getLocalizedMessage(), ex);
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    protected MessageSource getMessageSource() {
        return this.messageSource;
    }
}

