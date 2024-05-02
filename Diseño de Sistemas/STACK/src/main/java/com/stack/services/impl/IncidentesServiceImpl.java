package com.stack.services.impl;

import com.stack.entity.Comunidad;
import com.stack.entity.Incidente;
import com.stack.repositories.IncidenteRepository;
import com.stack.services.IncidentesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentesServiceImpl implements IncidentesService {
    private IncidenteRepository repository;

    public IncidentesServiceImpl(IncidenteRepository repoIncidentes) {
        this.repository = repoIncidentes;
    }

    public Incidente findById(Long id) {
        return repository.findById(id).get();
    }

    public List<Incidente> findIncidentesDeComunidad(Comunidad comunidad) {
        return repository.findByComunidad(comunidad);
    }

    public List<Incidente> findIncidentesCerradosDeComunidad(Comunidad comunidad) {
        return repository.findByComunidad(comunidad).stream().filter(incidente -> !incidente.estaAbierto()).toList();
    }

    public List<Incidente> findIncidentesAbiertosDeComunidad(Comunidad comunidad) {
        return repository.findByComunidad(comunidad).stream().filter(Incidente::estaAbierto).toList();
    }
}
