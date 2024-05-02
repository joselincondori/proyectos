package models.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import models.entities.domain.entidades.Entidad;
import models.entities.domain.entidades.Establecimiento;
import models.entities.domain.servicio.PrestacionServicio;
import models.entities.domain.servicio.Servicio;

import javax.inject.Singleton;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class PrestacionServicioRepository implements ICrudRepository, WithSimplePersistenceUnit {

    @Override
    public List findAll() {
        return entityManager().createQuery("from PrestacionServicio p where p.activo = true").getResultList();
    }

    @Override
    public Object findById(Long id) {
        return entityManager().find(PrestacionServicio.class, id);
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
        PrestacionServicio prestacionServicio = (PrestacionServicio) o;
        prestacionServicio.setActivo(false);
        this.update(prestacionServicio);
    }

    public List<PrestacionServicio> getPrestacionesByEstablecimientoId(Long establecimientoId) {
        String hql = "from PrestacionServicio p where p.establecimiento.id=:establecimientoId and p.activo=true";
        Query query = entityManager().createQuery(hql);
        query.setParameter("establecimientoId", establecimientoId);
        return query.getResultList();
    }

    public List<PrestacionServicio> getPrestacionesByServicioId(Long servicioId) {
        String hql = "from PrestacionServicio p where p.servicio.id=:id and p.activo=true";
        Query query = entityManager().createQuery(hql);
        query.setParameter("id", servicioId);
        return query.getResultList();
    }
}
