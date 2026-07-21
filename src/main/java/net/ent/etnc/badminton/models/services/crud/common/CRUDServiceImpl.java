package net.ent.etnc.badminton.models.services.crud.common;

import lombok.Getter;
import net.ent.etnc.badminton.models.entity.AbstractEntity;
import net.ent.etnc.badminton.models.entity.communs.ValidUtils;
import net.ent.etnc.badminton.models.entity.communs.exceptions.ValidException;
import net.ent.etnc.badminton.models.services.crud.common.exceptions.ServiceException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class CRUDServiceImpl<T extends AbstractEntity, R extends JpaRepository<T, Long>> implements CRUDService<T> {

    @Getter
    protected R monrepo;

    @Override
    public T findById(Long idT) throws ServiceException {
        try {
            Optional<T> optionalT = this.monrepo.findById(idT);
            if (optionalT.isEmpty()) throw new ServiceException("L'objet n'a pas été trouvé");
            return optionalT.get();
        } catch (IllegalArgumentException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public T save(T t) throws ServiceException {
        try {
            return this.monrepo.save(t);
        } catch (OptimisticLockingFailureException | IllegalArgumentException e) {
            throw new ServiceException(e.getMessage(), e);
        }

    }

    @Override
    public void deleteById(Long idT) throws ServiceException {
        try {
            this.monrepo.deleteById(idT);
        } catch (OptimisticLockingFailureException | IllegalArgumentException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean existsById(Long idT) throws ServiceException {
        try {
            return this.monrepo.existsById(idT);
        } catch (IllegalArgumentException e) {
            throw new ServiceException(e.getMessage(), e);
        }

    }

    @Override
    public boolean isValid(T t) throws ServiceException {
        try {
            ValidUtils.validate(t);
            return true;
        } catch (ValidException e) {
            throw new ServiceException("L'objet n'est pas valide", e);
        }
    }

    @Override
    public List<T> findAll() throws ServiceException {
        try {
            return this.monrepo.findAll();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }

    }

    @Override
    public List<T> saveAll(List<T> objects) throws ServiceException{
        try {
            return this.monrepo.saveAll(objects);
        } catch (OptimisticLockingFailureException | IllegalArgumentException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public long count() throws ServiceException {
        try {
            return this.monrepo.count();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}