package common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.ws.rs.NotFoundException;

@Getter
@ToString
@AllArgsConstructor
public class NotFoundObjectException extends NotFoundException {

    private Object objectId;

}

