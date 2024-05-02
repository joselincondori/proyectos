package models.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import models.entities.domain.comunidad.Comunidad;
import models.entities.domain.comunidad.Miembro;
import models.entities.domain.persona.Persona;
import models.entities.domain.servicio.PrestacionServicio;
import models.entities.sesion.Usuario;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class MiembroRepository implements ICrudRepository, WithSimplePersistenceUnit {
    @Override
    public List findAll() {
        return entityManager().createQuery("from " + Miembro.class.getName()).getResultList();
    }

    @Override
    public Object findById(Long id) {
        return entityManager().find(Miembro.class, id);
    }
    public Miembro getMiembroByPersonaAndComunidad(Persona persona, Comunidad comunidad) {
        String hql = "from Miembro m where m.persona = :persona and m.comunidad = :comunidad";
        Query query = entityManager().createQuery(hql);
        query.setParameter("persona", persona);
        query.setParameter("comunidad", comunidad);
        return (Miembro) query.getSingleResult();
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

    public void deleteAll(List<Long> ids) {
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();
        String hql = "DELETE Miembro e WHERE id IN (:ids)";
        Query query = entityManager().createQuery(hql);
        query.setParameter("ids", ids);
        query.executeUpdate();
        tx.commit();
    }
}
