package com.stack.repositories;

import com.stack.entity.Comunidad;
import com.stack.entity.Incidente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidenteRepository extends JpaRepository<Incidente, Long> {
    List<Incidente> findByComunidad(Comunidad comunidad);
}
