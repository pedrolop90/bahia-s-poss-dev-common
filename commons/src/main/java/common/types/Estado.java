package common.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import common.exception.InvalidEnumValueException;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Estado {
    ACTIVO, INACTIVO;

    @JsonValue
    public String getValue() {
        return name();
    }

    @JsonCreator
    public static Estado fromValue(String value) {
        if (value != null && value.isEmpty()) {
            return null;
        }
        for (Estado p : values()) {
            if (p.name().equals(value)) {
                return p;
            }
        }
        throw new InvalidEnumValueException("Estado", value);
    }
}
