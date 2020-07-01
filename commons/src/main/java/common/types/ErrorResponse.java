package common.types;

import com.fasterxml.jackson.annotation.JsonFormat;
import common.util.Constants;
import common.util.UtilStackTrace;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ErrorResponse extends BaseResponse {
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = Constants.FORMAT_DATE_TIME
    )
    private LocalDateTime createdDate;
    private String debugMessage;

    public ErrorResponse(@NotNull HttpStatus status, String message) {
        this(status.value(), message);
    }

    public ErrorResponse(int statusCode, String message) {
        this(statusCode, message, (Throwable) null);
    }

    public ErrorResponse(@NotNull HttpStatus status, Throwable ex) {
        this(status.value(), ex);
    }

    public ErrorResponse(int statusCode, Throwable ex) {
        this(statusCode, ex.getLocalizedMessage(), ex);
    }

    public ErrorResponse(HttpStatus status, String message, Throwable ex) {
        this(status.value(), message, ex);
    }

    public ErrorResponse(int statusCode, String message, Throwable ex) {
        super(false, statusCode, message);
        this.createdDate = LocalDateTime.now();
        this.debugMessage = UtilStackTrace.getStackTrace(ex);
    }
}

