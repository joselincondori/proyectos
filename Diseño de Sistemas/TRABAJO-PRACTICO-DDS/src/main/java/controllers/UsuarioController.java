package controllers;

import io.javalin.http.Context;
import models.entities.domain.comunidad.Comunidad;
import models.entities.domain.entidades.Entidad;
import models.entities.sesion.Usuario;
import models.repositories.LocalidadRepository;
import models.repositories.MunicipioRepository;
import models.repositories.UsuarioRepository;
import server.utils.ICrudViewsHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UsuarioController extends Controller {


    private final UsuarioRepository usuarioRepository;

    public UsuarioController() {
        this.usuarioRepository = new UsuarioRepository();
    }

    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<Usuario> usuarios = this.usuarioRepository.findAll();
        model.put("usuarios", usuarios);
        context.render("admin-plataforma/admin-usuarios.hbs", model);
    }

    public void desactivarUsuario(Context context) {
        long idUsuario = Long.parseLong(context.formParam("id"));
        Usuario usuario = (Usuario) this.usuarioRepository.findById(idUsuario);

        if (usuario != null) {
            usuario.setEsValido(false);
            usuarioRepository.update(usuario);
        }

        context.redirect("/app/admin-plataforma/usuarios");
    }

    public void activarUsuario(Context context) {
        long idUsuario = Long.parseLong(context.formParam("id"));
        Usuario usuario =(Usuario) this.usuarioRepository.findById(idUsuario);

        if (usuario != null) {
            usuario.setEsValido(true);
            usuarioRepository.update(usuario);
        }

        context.redirect("/app/admin-plataforma/usuarios");
    }
}
