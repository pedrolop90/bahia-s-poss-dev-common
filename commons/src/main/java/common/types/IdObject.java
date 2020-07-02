package common.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class IdObject<ID> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private ID id;

    public int hashCode() {
        int prime = 1;
        int result = 31 * prime + (this.getId() == null ? 0 : this.getId().hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        else if (obj != null && this.getClass() == obj.getClass()) {
            IdObject<ID> other = (IdObject) obj;
            if (this.getId() == null) {
                return other.getId() == null;
            }
            else {
                return this.getId().equals(other.getId());
            }
        }
        else {
            return false;
        }
    }

    @JsonIgnore
    public boolean isNewObject() {
        return this.getId() == null;
    }

    public interface Attributes {
        String ID = "id";
    }
}
