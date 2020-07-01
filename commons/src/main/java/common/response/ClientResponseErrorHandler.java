package common.response;

import common.exception.APIExceptions;
import common.types.ErrorResponse;
import common.types.ValidationErrorResponse;
import common.util.UtilInputStream;
import common.util.UtilJson;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

@NoArgsConstructor
public class ClientResponseErrorHandler extends DefaultResponseErrorHandler {

    public void handleError(ClientHttpResponse response) throws IOException {
        String bodyJson = UtilInputStream.getString(response.getBody());
        ErrorResponse error = this.extractErrorResponse(bodyJson);
        String message = this.getErrorMessage(error, bodyJson);
        ValidationErrorResponse validation = this.extractValidationResponse(bodyJson);
        if (HttpStatus.UNAUTHORIZED.equals(response.getStatusCode())) {
            throw APIExceptions.noAutorizado(message);
        }
        else if (HttpStatus.NOT_FOUND.equals(response.getStatusCode())) {
            throw APIExceptions.objetoNoEncontrado(message);
        }
        else if (HttpStatus.BAD_REQUEST.equals(response.getStatusCode())) {
            if (validation != null) {
                throw APIExceptions.errorValidation(validation.getErrors());
            }
            else {
                throw APIExceptions.solicitudIncorrecta(message);
            }
        }
        else if (error != null) {
            throw APIExceptions.errorResponse(error);
        }
        else {
            super.handleError(response);
        }
    }

    private ValidationErrorResponse extractValidationResponse(String bodyJson) {
        return (ValidationErrorResponse) UtilJson.toObject(bodyJson, ValidationErrorResponse.class);
    }

    private ErrorResponse extractErrorResponse(String bodyJson) {
        return (ErrorResponse) UtilJson.toObject(bodyJson, ErrorResponse.class);
    }

    private String getErrorMessage(ErrorResponse errorResponse, String bodyJson) {
        return errorResponse == null ? bodyJson : errorResponse.getMessage();
    }
}

