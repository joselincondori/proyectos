package models.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import models.entities.domain.comunidad.Miembro;
import models.entities.domain.incidentes.Incidente;
import models.entities.domain.incidentes.SugerenciaRevision;
import models.entities.domain.persona.Persona;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class SugerenciaRevisionRepository implements WithSimplePersistenceUnit, ICrudRepository {

    public List<SugerenciaRevision> getSugerenciasPendientesDePersonaById(Long personaId) {
        String hql = "from SugerenciaRevision s where s.analizador.id=:id and s.fechaHoraResolucion=null";
        Query query = entityManager().createQuery(hql);
        query.setParameter("id", personaId);
        return query.getResultList();
    }

    public List<SugerenciaRevision> getSugerenciasNoResueltasDeIncidente(Incidente incidente) {
        Query query = entityManager().createQuery("from SugerenciaRevision s where s.fechaHoraResolucion=null and s.incidente=:incidente");
        query.setParameter("incidente", incidente);
        return query.getResultList();
    }

    public SugerenciaRevision getSugerenciaAbiertaByPersonaAndIncidente(Persona persona, Incidente incidente) {
        Query query = entityManager().createQuery("from SugerenciaRevision s where s.fechaHoraResolucion=null and s.incidente=:incidente and s.analizador=:analizador");
        query.setParameter("incidente", incidente);
        query.setParameter("analizador", persona);
        return (SugerenciaRevision) query.getSingleResult();
    }

    @Override
    public List findAll() {
        return entityManager().createQuery("from " + SugerenciaRevision.class.getName()).getResultList();
    }

    @Override
    public Object findById(Long id) {
        return entityManager().find(SugerenciaRevision.class, id);
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
