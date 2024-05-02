package com.stack.services.impl;

import com.stack.entity.Comunidad;
import com.stack.repositories.ComunidadRepository;
import com.stack.services.ComunidadService;
import org.springframework.stereotype.Service;

@Service
public class ComunidadServiceImpl implements ComunidadService {
    private ComunidadRepository repository;

    public ComunidadServiceImpl(ComunidadRepository repository) {
        this.repository = repository;
    }

    public Comunidad findById(Long id) {
        return repository.findById(id).get();
    }
}
