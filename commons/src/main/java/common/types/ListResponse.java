package common.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ListResponse<T> extends BaseResponse {

    private long count;
    private List<T> data;

    public ListResponse(String message) {
        super(false, HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public ListResponse(HttpStatus status, String message) {
        super(false, status.value(), message);
    }

    public ListResponse(int statusCode, String message) {
        super(false, statusCode, message);
    }

    public ListResponse(List<T> data) {
        this("Success", data);
    }

    public ListResponse(List<T> data, long count) {
        this("Success", data);
        this.count = count;
    }

    public ListResponse(String message, List<T> data) {
        super(true, HttpStatus.OK, message);
        this.data = data;
        this.count = data == null ? 0L : (long) data.size();
    }
}
