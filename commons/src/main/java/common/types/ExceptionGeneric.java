package common.types;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

public interface ExceptionGeneric<T> {

    String message();

    ResponseEntity<Object> handlerResponse(T ex, WebRequest request);
}
