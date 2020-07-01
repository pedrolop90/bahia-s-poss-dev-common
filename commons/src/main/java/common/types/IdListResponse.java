package common.types;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class IdListResponse extends ListResponse<Long> {

    public IdListResponse(List<Long> ids) {
        super(ids);
    }

    public IdListResponse(long count, List<Long> ids) {
        super(ids, count);
    }
}
