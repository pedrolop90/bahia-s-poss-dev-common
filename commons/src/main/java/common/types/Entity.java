package common.types;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public abstract class Entity<ID extends Serializable> extends IdObject<ID> implements Serializable {
    private static final long serialVersionUID = -7716070975924354714L;

    public interface Attributes extends IdObject.Attributes {
    }
}
