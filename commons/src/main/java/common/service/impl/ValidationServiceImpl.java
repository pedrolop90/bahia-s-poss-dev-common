package common.service.impl;


import common.exception.ValidationException;
import common.service.ValidationService;
import common.validation.ValidationError;
import common.validation.Validator;
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
public final class ValidationServiceImpl implements ValidationService {

    @Autowired(required = false)
    private Set<Validator<?>> validators;
    private Map<Class<?>, Map<Class<?>, List<Validator<?>>>> validatorsByScopeAndType = new HashMap();


    @PostConstruct
    public void afterPropertiesSet() {
        if (this.validators != null) {
            validators.forEach(validator -> {
                this.addValidator(validator);
            });
        }
    }

    @Override
    public <T> void validate(T domainBean, Class<?> action) {
        List<ValidationError> errors = this.validateAndGet(domainBean, action);
        if (errors != null && !errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    @Override
    public <T> void validate(T domainBean, Collection<Class<?>> actions) {
        List<ValidationError> errors = new ArrayList<>();
        actions.forEach(action -> errors.addAll(this.validateAndGet(domainBean, action)));
        if (errors != null && !errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    @Override
    public <T> void validate(Collection<T> domainBeans, Class<?> actions) {
        List<ValidationError> errors = new ArrayList<>();
        domainBeans.forEach(t -> errors.addAll(this.validateAndGet(t, actions)));
        if (errors != null && !errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private <T> List<ValidationError> validateAndGet(T domainBean, Class<?> action) {
        List<ValidationError> errors = new ArrayList();
        List<Validator<T>> validatorsList = this.getValidatorsOfObjectByScope(domainBean, action);
        for (Validator<T> validator : validatorsList) {
            List<ValidationError> temp = validator.validate(domainBean);
            errors.addAll(temp);
            if (!validator.isBreakingValidation()) {
                break;
            }
        }
        return errors;
    }


    private <T> List<Validator<T>> getValidatorsOfObjectByScope(T object, Class<?> scope) {
        Map<Class<?>, List<Validator<?>>> validatorsByScope = this.validatorsByScopeAndType
                .computeIfAbsent(object.getClass(), (c) -> new HashMap());
        return (List) validatorsByScope.computeIfAbsent(scope, (s) -> new ArrayList());
    }

    private void addValidator(Validator<?> validator) {
        Class<?> type = (Class) ((ParameterizedType) validator.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
        Map<Class<?>, List<Validator<?>>> validatorsByScope = this.validatorsByScopeAndType.computeIfAbsent(type, (t) -> new HashMap());
        validator.getScopes().forEach(scope -> {
            List<Validator<?>> validatorsList = validatorsByScope.computeIfAbsent(scope, (s) -> new ArrayList());
            validatorsList.add(validator);
        });
    }
}
