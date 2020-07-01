package common.validation;

import java.util.List;

public interface Interceptor<T> {
    void exec(T var1);

    List<Class<?>> getScopes();
}
