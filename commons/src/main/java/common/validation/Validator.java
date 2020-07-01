package common.validation;

import java.util.List;

public interface Validator<T> {
    List<ValidationError> validate(T var1);

    List<Class<?>> getScopes();

    default boolean isBreakingValidation() {
        return true;
    }
}
