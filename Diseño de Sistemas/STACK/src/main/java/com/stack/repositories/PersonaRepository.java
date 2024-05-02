package com.stack.repositories;

import com.stack.entity.Persona;
import com.stack.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Persona findByUsuario(Usuario usuario);
}
