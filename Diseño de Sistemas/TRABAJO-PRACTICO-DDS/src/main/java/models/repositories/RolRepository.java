package models.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import models.entities.domain.georef.Municipio;
import models.entities.domain.roles.Rol;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class RolRepository implements WithSimplePersistenceUnit, ICrudRepository {
    @Override
    public List findAll() {
        return entityManager().createQuery("from " + Rol.class.getName()).getResultList();
    }

    public Rol getRolAdminComunidad() {
        String hql = "from Rol r where r.descripcion = :descripcion";
        Query query = entityManager().createQuery(hql);
        query.setParameter("descripcion", "admin_comunidad");
        return (Rol) query.getSingleResult();
    }

    public Rol getRolAdminPlataforma() {
        String hql = "from Rol r where r.descripcion = :descripcion";
        Query query = entityManager().createQuery(hql);
        query.setParameter("descripcion", "admin_plataforma");
        return (Rol) query.getSingleResult();
    }

    @Override
    public Object findById(Long id) {
        return entityManager().find(Rol.class, id);
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
