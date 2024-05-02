package models.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import models.entities.domain.georef.Localidad;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class LocalidadRepository implements WithSimplePersistenceUnit, ICrudRepository{
    @Override
    public List findAll() {
        return entityManager().createQuery("from " + Localidad.class.getName()).getResultList();
    }

    @Override
    public Object findById(Long id) {
        return entityManager().find(Localidad.class, id);
    }

    @Override
    public void save(Object o) {
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager().persist(o);
        tx.commit();
    }

    public void saveAll(List<Localidad> localidades) {
        for(Localidad localidad : localidades)
            this.save(localidad);
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

    public List<Localidad> getAllByMunicipioId(Long id) {
        String hql = "from Localidad l where l.municipio.id = :id";
        Query query = entityManager().createQuery(hql);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
