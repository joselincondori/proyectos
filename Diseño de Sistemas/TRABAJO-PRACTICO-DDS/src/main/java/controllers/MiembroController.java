package controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import models.entities.domain.comunidad.Comunidad;
import models.entities.domain.comunidad.Miembro;
import models.entities.domain.entidades.OrganismoDeControl;
import models.entities.domain.persona.Persona;
import models.entities.sesion.Usuario;
import models.repositories.*;
import server.utils.ICrudViewsHandler;

import javax.persistence.NoResultException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MiembroController extends Controller implements ICrudViewsHandler {
    ComunidadRepository comunidadRepository;
    MiembroRepository miembroRepository;
    PersonaRepository personaRepository;
    UsuarioRepository usuarioRepository;
    public MiembroController(){
        this.comunidadRepository = new ComunidadRepository();
        this.miembroRepository = new MiembroRepository();
        this.personaRepository = new PersonaRepository();
        this.usuarioRepository = new UsuarioRepository();
    }
    @Override
    public void index(Context context) {
        Usuario usuario = usuarioLogueado(context);
        Persona persona = personaRepository.getPersonaByUsuario(usuario);
        Comunidad comunidad = (Comunidad) this.comunidadRepository.findById(Long.parseLong(context.pathParam("id")));
        Miembro miembroUser = null;

        try {
             miembroUser = miembroRepository.getMiembroByPersonaAndComunidad(persona, comunidad);
        } catch (NoResultException ignored) {}

        Map<String, Object> model = new HashMap<>();
        model.put("comunidad", comunidad);
        model.put("miembros", comunidad.getMiembros());
        model.put("miembroUser", miembroUser);
        context.render("comunidades/comunidades-miembros/miembros.hbs", model);
    }

    @Override
    public void show(Context context) {
        Miembro miembro = (Miembro) this.miembroRepository.findById(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("miembro", miembro);
        context.render("comunidadess/comunidades.hbs", model);
    }

    @Override
    public void create(Context context) {
        Miembro miembro = null;
        Map<String, Object> model = new HashMap<>();
        model.put("miembro", miembro);
        context.render("comunidades/comunidad-create.hbs", model);
    }

    @Override
    public void save(Context context) {
        try {
            Miembro miembro = new Miembro();
            Persona persona = new Persona();
            Usuario usuario = new Usuario();
            Comunidad comunidad = new Comunidad();

            this.asignarParametros(persona, usuario, comunidad, context);

            persona.setUsuario(usuario);
            miembro.setPersona(persona);
            miembro.setComunidad(comunidad);

            this.usuarioRepository.save(usuario);
            this.personaRepository.save(persona);
            this.miembroRepository.save(miembro);
            context.status(HttpStatus.CREATED);
            context.redirect("/app/miembros");

        } catch (Exception e) {
            e.printStackTrace();
            context.status(HttpStatus.INTERNAL_SERVER_ERROR);
            context.result("Error al crear el miembro.");
        }
    }

    @Override
    public void edit(Context context) {
        Miembro miembro = (Miembro) this.miembroRepository.findById(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("miembro", miembro);
        context.render("comunidades/comunidades-miembros/miembro-edit.hbs", model);
    }

    @Override
    public void update(Context context) {
        Miembro miembro = (Miembro) this.miembroRepository.findById(Long.parseLong(context.pathParam("id")));
        Persona persona = (Persona) this.personaRepository.findById(Long.parseLong(context.pathParam("idPersona")));
        Usuario usuario = (Usuario) this.usuarioRepository.findById(Long.parseLong(context.pathParam("idUsuario")));
        Comunidad comunidad = (Comunidad) this.comunidadRepository.findById(Long.parseLong(context.pathParam("idComunidad")));
        this.asignarParametros(persona, usuario, comunidad, context);
        this.usuarioRepository.update(usuario);
        this.personaRepository.update(persona);
        this.miembroRepository.update(miembro);
        context.redirect("/app/comunidades");
    }

    @Override
    public void delete(Context context) {
        Miembro miembro = (Miembro) this.miembroRepository.findById(Long.parseLong(context.pathParam("id")));
        this.miembroRepository.delete(miembro);
        context.redirect("/app/comunidades");
    }

    private void asignarParametros(Persona persona, Usuario usuario, Comunidad comunidad, Context context) {
        if(!Objects.equals(context.formParam("nombre"), "")) {
            comunidad.setNombre(context.formParam("nombre"));
        }
        if(!Objects.equals(context.formParam("nombrePersona"), "")){
            persona.setNombre(context.formParam("nombrePersona"));
        }
        if(!Objects.equals(context.formParam("mailPersona"), "")){
            persona.setMail(context.formParam("mailPersona"));
        }
        if(!Objects.equals(context.formParam("apellidoPersona"), "")){
            persona.setApellido(context.formParam("apellidoPersona"));
        }
        if(!Objects.equals(context.formParam("telefonoPersona"), "")){
            persona.setTelefono(context.formParam("telefonoPersona"));
        }
        if(!Objects.equals(context.formParam("usuario"), "")){
            usuario.setUsername(context.formParam("usuario"));
        }
    }
}
