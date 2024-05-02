package com.stack.services.impl;

import com.stack.entity.Usuario;
import com.stack.repositories.UsuarioRepository;
import com.stack.services.UsuarioService;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario findById(Long id) {
        return repository.findById(id).get();
    }
}
