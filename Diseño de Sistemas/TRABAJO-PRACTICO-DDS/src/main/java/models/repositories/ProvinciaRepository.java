package models.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import models.entities.domain.georef.Provincia;
import models.entities.domain.servicio.PrestacionServicio;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class ProvinciaRepository implements WithSimplePersistenceUnit, ICrudRepository {
    @Override
    public List findAll() {
        return entityManager().createQuery("from " + Provincia.class.getName()).getResultList();
    }

    @Override
    public Object findById(Long id) {
        return entityManager().find(Provincia.class, id);
    }

    @Override
    public void save(Object o) {
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager().persist(o);
        tx.commit();
    }

    public void saveAll(List<Provincia> provincias) {
        for(Provincia provincia : provincias) {
            this.save(provincia);
        }
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

    public Provincia getByNombre(String nombre) {
        String hql = "from Provincia p where p.nombre = :nombre";
        Query query = entityManager().createQuery(hql);
        query.setParameter("nombre", nombre);
        return (Provincia) query.getSingleResult();
    }
}
