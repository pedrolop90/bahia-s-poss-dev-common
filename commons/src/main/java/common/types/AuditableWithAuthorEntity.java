package common.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class AuditableWithAuthorEntity<ID extends Serializable> extends AuditableEntity<ID> {

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;


    public interface Attributes extends AuditableEntity.Attributes {
        String CREATED_BY = "createdBy";
        String UPDATED_BY = "updatedBy";
    }

}
