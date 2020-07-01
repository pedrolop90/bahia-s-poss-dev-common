package common.service.impl;

import com.google.common.collect.Streams;
import common.exception.APIExceptions;
import common.service.ConverterService;
import common.service.InterceptorService;
import common.service.Service;
import common.service.ValidationService;
import common.types.DomainBean;
import common.types.Entity;
import common.util.MessageKeys;
import common.validation.groups.Delete;
import common.validation.groups.Insert;
import common.validation.groups.Update;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

@NoArgsConstructor
abstract class ServiceImpl<D extends DomainBean<ID>, E extends Entity<ID>, ID extends Serializable>
        implements Service<D, ID> {

    @Autowired
    private ConverterService converterService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private InterceptorService interceptorService;

    private Class<D> domainClass = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    private Class<E> entityClass = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];

    @Transactional(
            propagation = Propagation.REQUIRED
    )
    public D insert(D domainBean) {
        this.getInterceptorService().interceptor(domainBean, Insert.class);
        this.getValidationService().validate(domainBean, Insert.class);
        E entity = (E) this.converterService.convertTo(domainBean, this.entityClass);
        this.getDao().save(entity);
        domainBean.setId(entity.getId());
        return domainBean;
    }


    @Transactional(
            propagation = Propagation.REQUIRED
    )
    public List<D> insert(List<D> domainBeans) {
        this.getInterceptorService().interceptor(domainBeans, Insert.class);
        this.getValidationService().validate(domainBeans, Insert.class);
        List<E> entities = this.converterService.convertTo(domainBeans, this.entityClass);
        Iterable<E> entitiesSaved = getDao().saveAll(entities);
        return getConverterService().convertTo(entitiesSaved, domainClass);
    }

    @Transactional(
            propagation = Propagation.REQUIRED
    )
    public void update(D domainBean) {
        this.getInterceptorService().interceptor(domainBean, Update.class);
        this.getValidationService().validate(domainBean, Update.class);
        E entity = (E) this.converterService.convertTo(domainBean, this.entityClass);
        this.getDao().save(entity);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED
    )
    public List<D> update(List<D> domainBeans) {
        this.getInterceptorService().interceptor(domainBeans, Update.class);
        this.getValidationService().validate(domainBeans, Update.class);
        List<E> entities = this.converterService.convertTo(domainBeans, this.entityClass);
        Iterable<E> entitiesSaved = getDao().saveAll(entities);
        return converterService.convertTo(entitiesSaved, domainClass);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED
    )
    public void update(ID id, D domainBean) {
        domainBean.setId(id);
        this.getInterceptorService().interceptor(domainBean, Update.class);
        this.getValidationService().validate(domainBean, Update.class);
        E entity = (E) this.converterService.convertTo(domainBean, this.entityClass);
        this.getDao().save(entity);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED
    )
    public void delete(ID id) {
        Optional.ofNullable(this.findById(id)).ifPresent((obj) -> {
            this.getInterceptorService().interceptor(obj, Delete.class);
            this.getValidationService().validate(obj, Delete.class);
        });
        this.getDao().deleteById(id);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED
    )
    public void delete(D domainBean) {
        this.getInterceptorService().interceptor(domainBean, Delete.class);
        this.getValidationService().validate(domainBean, Delete.class);
        this.delete(domainBean.getId());
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED
    )
    public void delete(List<ID> ids) {
        List<E> entities = Streams.stream(getDao().findAllById(ids)).collect(Collectors.toList());
        List<D> domainBeans = getConverterService().convertTo(entities, domainClass);
        this.getInterceptorService().interceptor(domainBeans, Delete.class);
        this.getValidationService().validate(domainBeans, Delete.class);
        getDao().deleteAll(entities);
    }

    @Override
    public D findById(ID id) {
        return (D) this.findById(id, this.domainClass);
    }

    @Override
    public D findByIdNotFound(ID id) {
        D domainBean = Optional.ofNullable(this.findById(id, this.domainClass))
                .orElseThrow(() ->
                        APIExceptions.objetoNoEncontrado(
                                getMessageSource()
                                        .getMessage(MessageKeys.NOT_FOUND, null, getLocale())
                        )
                );
        return domainBean;
    }

    @Override
    public List<D> findById(List<ID> ids) {
        List<E> entities = Streams.stream(getDao().findAllById(ids)).collect(Collectors.toList());
        return getConverterService().convertTo(entities, domainClass);
    }

    public <R> R findById(ID id, Class<R> typeClass) {
        Optional<E> entity = this.getDao().findById(id);
        return entity.map((e) -> {
            return this.converterService.convertTo(e, typeClass);
        }).orElse(null);
    }

    public List<D> findAll() {
        return this.findAll(this.domainClass);
    }

    public <R> List<R> findAll(Class<R> typeClass) {
        Iterable<E> entities = this.getDao().findAll();
        return this.converterService.convertTo(entities, typeClass);
    }

    protected abstract CrudRepository<E, ID> getDao();

    protected ConverterService getConverterService() {
        return this.converterService;
    }

    protected ValidationService getValidationService() {
        return this.validationService;
    }

    protected InterceptorService getInterceptorService() {
        return this.interceptorService;
    }

    protected MessageSource getMessageSource() {
        return this.messageSource;
    }
}