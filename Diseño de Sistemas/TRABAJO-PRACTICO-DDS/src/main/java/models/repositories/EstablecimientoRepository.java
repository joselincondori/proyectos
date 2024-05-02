package models.repositories;

import controllers.dtos.EstablecimientoDto;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import models.entities.domain.entidades.Establecimiento;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class EstablecimientoRepository implements ICrudRepository, WithSimplePersistenceUnit {
    @Override
    public List findAll() {
        return entityManager().createQuery("from Establecimiento where activo=true").getResultList();
    }

    @Override
    public Object findById(Long id) {
        return entityManager().find(Establecimiento.class, id);
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

        Establecimiento establecimiento = (Establecimiento) o;
        establecimiento.setActivo(false);
        this.update(establecimiento);
    }

    public List<Establecimiento> getEstablecimientosDeEntidadByEntidadId(Long entidadId) {
        String hql = "from Establecimiento e where e.entidad.id = :id";

        Query query = entityManager().createQuery(hql);
        query.setParameter("id", entidadId);
        return query.getResultList();
    }
}