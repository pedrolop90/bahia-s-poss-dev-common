package common.persistence;

import lombok.NoArgsConstructor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Time;
import java.time.LocalTime;

@NoArgsConstructor
@Converter(
        autoApply = true
)
public class LocalTimeAttributeConverter implements AttributeConverter<LocalTime, Time> {

    public Time convertToDatabaseColumn(LocalTime locTime) {
        return locTime == null ? null : Time.valueOf(locTime);
    }

    public LocalTime convertToEntityAttribute(Time dbTime) {
        return dbTime == null ? null : dbTime.toLocalTime();
    }
}

