package common.types;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
public abstract class ObjectResponse<T> extends BaseResponse {

    private T data;

    public ObjectResponse(T data) {
        this(HttpStatus.OK, data);
    }

    public ObjectResponse(String message, T data) {
        super(true, HttpStatus.OK, message);
        this.data = data;
    }

    public ObjectResponse(@NotNull HttpStatus status, T data) {
        super(true, status, status.getReasonPhrase());
        this.data = data;
    }

    public ObjectResponse(HttpStatus status, String message, T data) {
        super(true, status, message);
        this.data = data;
    }

}
