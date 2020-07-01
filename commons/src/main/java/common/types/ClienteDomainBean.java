package common.types;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public abstract class ClienteDomainBean<ID> extends DomainBean<ID> {

    public interface Attributes extends DomainBean.Attributes {
        String CLIENTE_ID = "clienteId";
    }
}
