package common.service;

import java.util.Collection;

public interface ValidationService {
    <T> void validate(T domainBean, Class<?> action);

    <T> void validate(T domainBean, Collection<Class<?>> action);

    <T> void validate(Collection<T> domainBeans, Class<?> actions);
}

