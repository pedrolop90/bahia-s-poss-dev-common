package common.types;

import com.fasterxml.jackson.annotation.JsonFormat;
import common.util.Constants;
import common.validation.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ValidationErrorResponse extends BaseResponse {
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = Constants.FORMAT_DATE_TIME
    )
    private LocalDateTime createdDate;
    private List<ValidationError> errors;

    public ValidationErrorResponse(String message, ValidationError... errors) {
        this(message, Arrays.asList(errors));
    }

    public ValidationErrorResponse(String message, List<ValidationError> errors) {
        super(false, HttpStatus.BAD_REQUEST, message);
        this.errors = errors;
        this.createdDate = LocalDateTime.now();
    }
}
