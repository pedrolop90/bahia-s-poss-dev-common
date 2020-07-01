package common.service.impl;

import com.google.common.collect.Lists;
import common.service.ConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class ConverterServiceImpl extends DefaultConversionService implements ConverterService {
    @Autowired(
            required = false
    )
    private Set<Converter<?, ?>> converters;

    public ConverterServiceImpl() {
    }

    @Override
    public <S, T> T convertTo(S source, Class<T> targetClass) {
        return this.convert(source, targetClass);
    }

    @Override
    public <S, T> List<T> convertTo(Collection<S> source, Class<T> targetClass) {
        return convertTo(source, targetClass);
    }

    @Override
    public <S, T> List<T> convertTo(Iterable<S> source, Class<T> targetClass) {
        List<T> result = null;
        if (source != null) {
            result = Lists.newArrayList();
            Iterator var4 = source.iterator();

            while (var4.hasNext()) {
                S s = (S) var4.next();
                result.add(this.convert(s, targetClass));
            }
        }

        return result;
    }

    @Override
    public <S, T> boolean canConvertTo(Class<S> sourceClass, Class<T> targetClass) {
        return this.canConvert(sourceClass, targetClass);
    }

    @PostConstruct
    public void afterPropertiesSet() {
        if (this.converters != null) {
            Iterator var1 = this.converters.iterator();

            while (var1.hasNext()) {
                Converter<?, ?> converter = (Converter) var1.next();
                this.addConverter(converter);
            }
        }

    }

    public void setConverters(Set<Converter<?, ?>> converters) {
        this.converters = converters;
    }
}

