package common.types;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public abstract class DomainBean<ID> extends IdObject<ID> {

    public interface Attributes extends IdObject.Attributes {
    }

}
