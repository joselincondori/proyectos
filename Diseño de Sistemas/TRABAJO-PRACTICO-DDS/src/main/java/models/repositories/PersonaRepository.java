package models.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import models.entities.domain.persona.Persona;
import models.entities.sesion.Usuario;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class PersonaRepository implements WithSimplePersistenceUnit, ICrudRepository {

    public Persona getPersonaByUsuario(Usuario usuario) {
        String hql = "from Persona p where p.usuario = :usuario";
        Query query = entityManager().createQuery(hql);
        query.setParameter("usuario", usuario);
        return (Persona) query.getSingleResult();
    }

    @Override
    public List findAll() {
        return entityManager().createQuery("from " + Persona.class.getName()).getResultList();
    }

    @Override
    public Object findById(Long id) {
        return entityManager().find(Persona.class, id);
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

