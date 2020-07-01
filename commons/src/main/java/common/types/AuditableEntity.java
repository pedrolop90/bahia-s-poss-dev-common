package common.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.cassandra.core.mapping.Column;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class AuditableEntity<ID extends Serializable> extends Entity<ID> {

    @CreatedDate
    @Column("created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column("updated_date")
    private LocalDateTime updatedDate;

    public interface Attributes extends Entity.Attributes {
        String CREATED_DATE = "createdDate";
        String UPDATED_DATE = "updatedDate";
    }
}
