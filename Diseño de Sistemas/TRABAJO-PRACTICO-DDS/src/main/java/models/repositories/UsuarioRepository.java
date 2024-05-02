package models.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import models.entities.sesion.Usuario;

import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class UsuarioRepository  implements WithSimplePersistenceUnit, ICrudRepository {

    @Override
    public List findAll() {
        return entityManager().createQuery("from " + Usuario.class.getName()).getResultList();
    }

    public Object buscarPorNombreUsuario(String username){
        String hql = "SELECT u FROM Usuario u WHERE u.username = :username AND u.username IS NOT NULL";
        Query query = entityManager().createQuery(hql, Usuario.class);
        query.setParameter("username", username);
        List<Usuario> usuarios = query.getResultList();
        if(usuarios.isEmpty()){
            return null;
        }
        else
            return usuarios.get(0);
    }
    @Override
    public Object findById(Long id) {
        return entityManager().find(Usuario.class, id);
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


