package common.types;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.Map;

@Getter
@Setter
@ToString
@UserDefinedType(MapEmbebido.Attributes.NOMBRE_TABLA)
public class MapEmbebido {

    public interface Attributes {
        String NOMBRE_TABLA = "map_embebido";
    }

    @Column("values")
    private Map<String, String> mapa;

}
