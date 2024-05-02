package models.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import models.entities.domain.georef.Municipio;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class MunicipioRepository implements WithSimplePersistenceUnit, ICrudRepository{
    @Override
    public List findAll() {
        return entityManager().createQuery("from " + Municipio.class.getName()).getResultList();
    }

    @Override
    public Object findById(Long id) {
        return entityManager().find(Municipio.class, id);
    }

    @Override
    public void save(Object o) {
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager().persist(o);
        tx.commit();
    }

    public void saveAll(List<Municipio> municipios) {
        for(Municipio municipio : municipios)
            this.save(municipio);
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

    public List<Municipio> getAllByProvinciaId(Long id) {
        String hql = "from Municipio m where m.provincia.id = :id";
        Query query = entityManager().createQuery(hql);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
