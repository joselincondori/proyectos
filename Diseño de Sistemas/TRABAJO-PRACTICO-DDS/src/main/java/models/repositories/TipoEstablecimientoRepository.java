package models.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import models.entities.domain.entidades.TipoEstablecimiento;

import javax.persistence.EntityTransaction;
import java.util.List;

public class TipoEstablecimientoRepository implements ICrudRepository, WithSimplePersistenceUnit {
    @Override
    public List findAll() {
        return entityManager().createQuery("from " + TipoEstablecimiento.class.getName()).getResultList();
    }

    @Override
    public Object findById(Long id) {
        return entityManager().find(TipoEstablecimiento.class, id);
    }

    @Override
    public void save(Object o) {
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager().persist(o);
        tx.commit();
    }

    @Override
    public void update(Object o) {
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager().merge(o);
        tx.commit();
    }

    @Override
    public void delete(Object o) {
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager().remove(o);
        tx.commit();
    }
}
