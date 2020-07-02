package common.types;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public abstract class ClienteEntity<ID extends Serializable> extends AuditableWithAuthorEntity<ID> {

    public interface Attributes extends AuditableWithAuthorEntity.Attributes {
        String CLIENTE_ID = "clienteId";
    }

    @Column(name = "cliente_id")
    private ID clienteId;

}
