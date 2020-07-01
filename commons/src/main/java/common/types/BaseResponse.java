package common.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class BaseResponse {

    private boolean success;
    private int statusCode;
    private String message;


    public BaseResponse(boolean success, @NotNull HttpStatus status, String message) {
        this.success = success;
        this.statusCode = status.value();
        this.message = message;
    }

    @JsonIgnore
    public HttpStatus getStatus() {
        HttpStatus[] var1 = HttpStatus.values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            HttpStatus status = var1[var3];
            if (status.value() == this.statusCode) {
                return status;
            }
        }

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @JsonIgnore
    public void setStatus(HttpStatus status) {
        this.statusCode = status.value();
    }

}

