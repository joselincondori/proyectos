package models.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import models.entities.domain.entidades.Entidad;
import models.entities.domain.servicio.Servicio;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class EntidadRepository implements WithSimplePersistenceUnit, ICrudRepository {

        @Override
        public List findAll() {
            return entityManager().createQuery("from Entidad e where e.activo=true").getResultList();
        }

        @Override
        public Object findById(Long id) {
            return entityManager().find(Entidad.class, id);
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

            Entidad entidad = (Entidad) o;
            entidad.setActivo(false);
            this.update(entidad);
        }
    public List<Entidad> findEntidadesNoControladas() {
        String hql = "select e from Entidad e where not exists " +
                    "(select 1 from EntidadPrestadora ep where e member of ep.entidadesQueControla) " +
                    "and e.activo = true";
        Query query = entityManager().createQuery(hql);
        return query.getResultList();
    }
    public List<Entidad> findEntidadesNoInteresadas(Long id) {
        String hql = "SELECT e FROM Entidad e WHERE e.activo = true AND e.id NOT IN (SELECT e.id FROM Persona p JOIN p.interes.entidades e WHERE p.id = :userId)";
        Query query = entityManager().createQuery(hql);
        query.setParameter("userId", id);
        return query.getResultList();
    }

}


