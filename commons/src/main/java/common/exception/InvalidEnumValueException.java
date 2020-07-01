package common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class InvalidEnumValueException extends RuntimeException {
    private String field;
    private String value;

}
