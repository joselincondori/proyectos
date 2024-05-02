package models.repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import models.entities.domain.comunidad.Comunidad;
import models.entities.domain.entidades.OrganismoDeControl;
import models.entities.domain.incidentes.Incidente;
import models.entities.domain.servicio.PrestacionServicio;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;
import java.util.Objects;

public class IncidenteRepository implements ICrudRepository, WithSimplePersistenceUnit {
    public List<Incidente> getIncidentesCerrados() {
        String hql = "from Incidente i where i.fechaHoraCierre != null";
        Query query = entityManager().createQuery(hql);
        return query.getResultList();
    }

    public List<Incidente> getIncidentesCerradosDeComunidad(Comunidad comunidad) {
        String hql = "from Incidente i where i.fechaHoraCierre!=null and i.comunidad=:comunidad";
        Query query = entityManager().createQuery(hql);
        query.setParameter("comunidad", comunidad);
        return query.getResultList();
    }

    public List<Incidente> getIncidentesAbiertos() {
        String hql = "from Incidente i where i.fechaHoraCierre = null";
        Query query = entityManager().createQuery(hql);
        return query.getResultList();
    }

    public List<Incidente> getIncidentesAbiertosDeComunidad(Comunidad comunidad) {
        String hql = "from Incidente i where i.fechaHoraCierre=null and i.comunidad=:comunidad";
        Query query = entityManager().createQuery(hql);
        query.setParameter("comunidad", comunidad);
        return query.getResultList();
    }

    public List<Incidente> getIncidentesDeComunidad(Comunidad comunidad){
        String hql = "from Incidente i where i.comunidad=:comunidad";
        Query query = entityManager().createQuery(hql);
        query.setParameter("comunidad", comunidad);
        return query.getResultList();
    }

    public List<Incidente> getIncidentesAbiertosDePrestacion(PrestacionServicio prestacionServicio) {
        String hql = "from Incidente i where i.prestacionServicio = :prestacion and i.fechaHoraCierre = null";
        Query query = entityManager().createQuery(hql);
        query.setParameter("prestacion", prestacionServicio);
        return query.getResultList();
    }

    public List<Incidente> getIncidenteCerradosDeComunidad(Long comunidadId){
        String hql = "from Incidente i where i.comunidad.id = :id and i.fechaHoraCierre != null";
        Query query = entityManager().createQuery(hql);
        query.setParameter("id", comunidadId);
        return query.getResultList();
    }

    public List<Incidente> getIncidentesDePersona(Long personaId) {
        String hql = "from Incidente i where i.creador.id = :id";
        Query query = entityManager().createQuery(hql);
        query.setParameter("id", personaId);
        return query.getResultList();
    }

    public List<Incidente> getIncidentesCerradosDePersona(Long personaId) {
        String hql = "from Incidente i where i.creador.id = :id and i.fechaHoraCierre != null";
        Query query = entityManager().createQuery(hql);
        query.setParameter("id", personaId);
        return query.getResultList();
    }

    @Override
    public List findAll() {
        return entityManager().createQuery("from " + Incidente.class.getName()).getResultList();
    }

    @Override
    public Object findById(Long id) {
        return entityManager().find(Incidente.class, id);
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