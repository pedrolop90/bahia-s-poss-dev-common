package common.service;

import java.util.Collection;
import java.util.List;

public interface ConverterService {
    <S, T> T convertTo(S var1, Class<T> var2);

    <S, T> List<T> convertTo(Collection<S> var1, Class<T> var2);

    <S, T> List<T> convertTo(Iterable<S> var1, Class<T> var2);

    <S, T> boolean canConvertTo(Class<S> var1, Class<T> var2);
}

