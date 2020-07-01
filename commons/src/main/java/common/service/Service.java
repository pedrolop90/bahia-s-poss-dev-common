package common.service;

import common.types.DomainBean;

import java.util.List;

public interface Service<D extends DomainBean<ID>, ID> {

    D insert(D domainBean);

    List<D> insert(List<D> domainBeans);

    void update(D domainBean);

    List<D> update(List<D> domainBeans);

    void update(ID id, D domainBean);

    void delete(ID id);

    void delete(D domainBean);

    void delete(List<ID> ids);

    D findById(ID id);

    D findByIdNotFound(ID id);

    List<D> findById(List<ID> ids);

    <R> R findById(ID id, Class<R> typeClass);

    List<D> findAll();

    <R> List<R> findAll(Class<R> typeClass);
}
