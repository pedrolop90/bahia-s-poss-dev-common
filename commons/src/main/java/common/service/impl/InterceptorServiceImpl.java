package common.service.impl;


import common.service.InterceptorService;
import common.validation.Interceptor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public final class InterceptorServiceImpl implements InterceptorService {

    @Autowired(required = false)
    private Set<Interceptor<?>> interceptors;
    private Map<Class<?>, Map<Class<?>, List<Interceptor<?>>>> interceptorsByDomainAndScope = new HashMap();


    @PostConstruct
    public void afterPropertiesSet() {
        if (this.interceptors != null) {
            interceptors.forEach(validator -> {
                this.addValidator(validator);
            });
        }
    }

    @Override
    public <T> void interceptor(T domainBean, Class<?> action) {
        this.validateAndGet(domainBean, action);
    }

    @Override
    public <T> void interceptor(T domainBean, Collection<Class<?>> actions) {
        actions.forEach(action -> this.validateAndGet(domainBean, action));
    }

    @Override
    public <T> void interceptor(Collection<T> domainBeans, Class<?> actions) {
        domainBeans.forEach(t -> this.validateAndGet(t, actions));
    }

    private <T> void validateAndGet(T domainBean, Class<?> action) {
        List<Interceptor<T>> validatorsList = this.getValidatorsOfObjectByScope(domainBean, action);
        validatorsList.forEach(validator -> validator.exec(domainBean));
    }

    private <T> List<Interceptor<T>> getValidatorsOfObjectByScope(T object, Class<?> scope) {
        Map<Class<?>, List<Interceptor<?>>> validatorsByScope = this.interceptorsByDomainAndScope
                .computeIfAbsent(object.getClass(), (c) -> new HashMap());
        return (List) validatorsByScope.computeIfAbsent(scope, (s) -> new ArrayList());
    }

    private void addValidator(Interceptor<?> validator) {
        Class<?> type = (Class) ((ParameterizedType) validator.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
        Map<Class<?>, List<Interceptor<?>>> validatorsByScope = this.interceptorsByDomainAndScope.computeIfAbsent(type, (t) -> new HashMap());
        validator.getScopes().forEach(scope -> {
            List<Interceptor<?>> validatorsList = validatorsByScope.computeIfAbsent(scope, (s) -> new ArrayList());
            validatorsList.add(validator);
        });
    }
}
