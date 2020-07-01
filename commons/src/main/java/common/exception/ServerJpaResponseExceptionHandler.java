package common.exception;

import lombok.NoArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@NoArgsConstructor
public abstract class ServerJpaResponseExceptionHandler extends ServerResponseExceptionHandler {

    @ExceptionHandler({
            EmptyResultDataAccessException.class,
            DataAccessResourceFailureException.class,
            ConstraintViolationException.class,
            DataIntegrityViolationException.class})
    public final ResponseEntity<Object> handleDBFailures(Exception ex, WebRequest request) throws Exception {
        HandleExceptionCallable handleExceptionCallable = HandleExceptionCallable.fromValue(ex.getClass());
        if (handleExceptionCallable != null) {
            String message = this.getMessageSource().getMessage(handleExceptionCallable.getMessageKey(), null, LocaleContextHolder.getLocale());
            return handleExceptionCallable.getExceptionCallable().callable(ex, request, message);
        }
        return this.handleException(ex, request);
    }
}
