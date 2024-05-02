package models.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import models.entities.domain.servicio.PrestacionServicio;
import models.entities.domain.servicio.Servicio;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class ServicioRepository implements ICrudRepository, WithSimplePersistenceUnit {
    @Override
    public List findAll() {
        return entityManager().createQuery("from Servicio s where s.activo=true").getResultList();
    }

    @Override
    public Object findById(Long id) {
        return entityManager().find(Servicio.class, id);
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
        Servicio servicio = (Servicio) o;
        servicio.setActivo(false);
        this.update(servicio);
    }

    public List<Servicio> findServiciosNoControlados() {
        String hql = "select s from Servicio s where s.organismo=null and s.activo=true";
        Query query = entityManager().createQuery(hql);
        return query.getResultList();
    }

    public List<Servicio> findServiciosNoInteresados(Long userId) {
        String hql = "SELECT s FROM Servicio s WHERE s.activo = true AND s.id NOT IN (SELECT s.id FROM Persona p JOIN p.interes.servicios s WHERE p.id = :userId)";
        Query query = entityManager().createQuery(hql);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
