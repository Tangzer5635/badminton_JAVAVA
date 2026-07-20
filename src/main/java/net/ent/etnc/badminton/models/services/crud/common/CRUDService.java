package net.ent.etnc.badminton.models.services.crud.common;

import net.ent.etnc.badminton.models.services.crud.common.exceptions.ServiceException;

import java.util.List;

public interface CRUDService<T> {
    T save(T t) throws ServiceException;

    T findById(Long idT) throws ServiceException;

    void deleteById(Long idT) throws ServiceException;

    boolean existsById(Long idT) throws ServiceException;

    boolean isValid(T batiment) throws ServiceException;

    List<T> findAll() throws ServiceException;

    List<T> saveAll(List<T> objects) throws ServiceException;

    long count() throws ServiceException;

}
