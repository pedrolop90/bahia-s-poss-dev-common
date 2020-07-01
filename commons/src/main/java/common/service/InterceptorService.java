package common.service;

import java.util.Collection;

public interface InterceptorService {
    <T> void interceptor(T domainBean, Class<?> action);

    <T> void interceptor(T domainBean, Collection<Class<?>> action);

    <T> void interceptor(Collection<T> domainBeans, Class<?> actions);
}

