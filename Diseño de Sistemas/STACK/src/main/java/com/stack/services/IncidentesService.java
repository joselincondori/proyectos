package com.stack.services;

import com.stack.entity.Comunidad;
import com.stack.entity.Incidente;

import java.util.List;

public interface IncidentesService {
    public Incidente findById(Long id);

    public List<Incidente> findIncidentesDeComunidad(Comunidad comunidad);

    public List<Incidente> findIncidentesCerradosDeComunidad(Comunidad comunidad);

    public List<Incidente> findIncidentesAbiertosDeComunidad(Comunidad comunidad);
}
