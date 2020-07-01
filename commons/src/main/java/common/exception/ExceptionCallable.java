package common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

@FunctionalInterface
public interface ExceptionCallable {

    ResponseEntity<Object> callable(Exception e, WebRequest request, String message);

}
