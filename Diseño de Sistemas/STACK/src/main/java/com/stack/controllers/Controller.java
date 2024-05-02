package com.stack.controllers;

import com.stack.entity.Usuario;
import com.stack.services.UsuarioService;
import jakarta.servlet.http.HttpSession;

public class Controller {
    protected UsuarioService repoUsuario;
    protected Usuario usuarioLogueado(HttpSession session) {
        if(session.getAttribute("usuario_id") == null)
            return null;
        Long usuarioId = (Long) session.getAttribute("usuario_id");
        return repoUsuario.findById(usuarioId);
    }
}
