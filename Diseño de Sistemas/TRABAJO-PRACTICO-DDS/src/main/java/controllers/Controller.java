package controllers;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import models.entities.sesion.Usuario;

import java.util.Objects;

public class Controller implements WithSimplePersistenceUnit {
    protected Usuario usuarioLogueado(Context ctx) {
        if(ctx.sessionAttribute("usuario_id") == null)
            return null;
        Long usuarioId = (Long) ctx.sessionAttribute("usuario_id");
        return entityManager().find(Usuario.class, usuarioId);
    }
}