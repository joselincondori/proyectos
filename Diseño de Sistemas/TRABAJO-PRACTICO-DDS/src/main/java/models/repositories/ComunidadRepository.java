package models.repositories;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import models.entities.domain.comunidad.Comunidad;
import models.entities.domain.persona.Persona;

import java.util.List;

public class ComunidadRepository implements WithSimplePersistenceUnit, ICrudRepository {

    public List<Comunidad> getComunidadesDePersona(Persona persona) {
        String hql = "select c from Comunidad c join c.miembros m where m.persona = :persona";
        Query query = entityManager().createQuery(hql);
        query.setParameter("persona", persona);
        return query.getResultList();
    }

    @Override
    public List findAll() {
        return entityManager().createQuery("from Comunidad where activa=true").getResultList();
    }

    @Override
    public Object findById(Long id) {
        return entityManager().find(Comunidad.class, id);
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

        Comunidad comunidad = (Comunidad) o;
        comunidad.setActiva(false);
        this.update(comunidad);
    }
}
