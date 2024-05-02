package controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NoContentResponse;
import models.entities.domain.comunidad.Comunidad;
import models.entities.domain.notificaciones.Notificacion;
import models.entities.domain.notificaciones.estrategia.Email.EstrategiaEmail;
import models.entities.domain.persona.Persona;
import models.entities.domain.roles.Rol;
import models.entities.sesion.Usuario;
import models.entities.domain.comunidad.Miembro;
import models.entities.domain.incidentes.Incidente;
import models.repositories.*;
import server.utils.ICrudViewsHandler;

import javax.persistence.NoResultException;
import java.util.*;

public class ComunidadesController extends Controller implements ICrudViewsHandler {

    ComunidadRepository comunidadRepository = new ComunidadRepository();
    MiembroRepository miembroRepository = new MiembroRepository();
    PersonaRepository personaRepository = new PersonaRepository();
    RolRepository rolRepository = new RolRepository();

    public ComunidadesController() {}

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<Comunidad> comunidades = this.comunidadRepository.findAll();
        model.put("comunidades", comunidades);
        context.render("admin-plataforma/admin-comunidades.hbs", model);
    }

    public void comunidadesParaUnirse(Context context) {
        Map<String, Object> model = new HashMap<>();
        Usuario usuario = usuarioLogueado(context);
        Persona persona = personaRepository.getPersonaByUsuario(usuario);
        List<Comunidad> comunidades = comunidadRepository.findAll();
        List<Comunidad> comunidadesPersona = this.comunidadRepository.getComunidadesDePersona(persona);
        comunidades.removeAll(comunidadesPersona);
        model.put("comunidades", comunidades);
        context.render("comunidades/unirse-comunidad.hbs", model);
    }

    public void sacarAdminMiembro(Context context) {
        Usuario usuario = usuarioLogueado(context);
        if(usuario.esAdmin()) {
            Miembro miembro = (Miembro) miembroRepository.findById(Long.parseLong(context.formParam("miembroId")));
            miembro.setRol(null);
            miembroRepository.update(miembro);
            Comunidad comunidad = miembro.getComunidad();
            comunidad.sacarAdministrador(miembro);
            comunidadRepository.update(comunidad);
            context.redirect("/app/comunidades/" + comunidad.getId() + "/edit");
        }
        context.redirect("/app/portal");
    }

    public void hacerAdminMiembro(Context context) {
        Usuario usuario = usuarioLogueado(context);
        if(usuario.esAdmin()) {
            Miembro miembro = (Miembro) miembroRepository.findById(Long.parseLong(context.formParam("miembroId")));
            Rol rolAdminComunidad = rolRepository.getRolAdminComunidad();
            miembro.setRol(rolAdminComunidad);
            miembroRepository.update(miembro);
            Comunidad comunidad = miembro.getComunidad();
            comunidad.agregarAdministrador(miembro);
            comunidadRepository.update(comunidad);
            context.redirect("/app/comunidades/" + comunidad.getId() + "/edit");
        }
        context.redirect("/app/portal");
    }

    public void eliminarMiembroComunidad(Context context) {
        Usuario usuario = usuarioLogueado(context);
        Persona persona = this.personaRepository.getPersonaByUsuario(usuario);
        Comunidad comunidad = (Comunidad) this.comunidadRepository.findById(Long.parseLong(context.formParam("comunidadId")));
        Miembro miembroUser = null;
        try {
            miembroUser = miembroRepository.getMiembroByPersonaAndComunidad(persona, comunidad);
        } catch (NoResultException ignored) {}

        if(usuario.esAdmin() || (miembroUser != null && miembroUser.esAdmin())) {
            Miembro miembroAEliminar = (Miembro) miembroRepository.findById(Long.parseLong(context.formParam("miembroId")));
            if(miembroUser != null && miembroAEliminar == miembroUser) {
                context.status(500);
                System.out.println("hola2");
                return;
            }
            miembroRepository.delete(miembroAEliminar);
            context.redirect("/app/comunidades/" + comunidad.getId() + "/miembros");
        } else
            context.status(401);
    }

    public void comunidadesUsuario(Context context) {
        Map<String, Object> model = new HashMap<>();
        Usuario usuario = super.usuarioLogueado(context);
        Persona persona = this.personaRepository.getPersonaByUsuario(usuario);
        List<Comunidad> comunidades = new ArrayList<>();
        List<Miembro> miembros = persona.getMiembros();
        for(Miembro miembro : miembros){
            comunidades.add(miembro.getComunidad());
        }
        model.put("usuario",usuario);
        model.put("comunidades", comunidades);
        context.render("comunidades/comunidades.hbs", model);
    }

    public void salirDeComunidad(Context context) {
        Usuario usuario = super.usuarioLogueado(context);
        Persona persona = this.personaRepository.getPersonaByUsuario(usuario);
        Comunidad comunidad = (Comunidad) this.comunidadRepository.findById(Long.parseLong(context.pathParam("id")));
        Miembro miembro = this.miembroRepository.getMiembroByPersonaAndComunidad(persona, comunidad);
        miembroRepository.delete(miembro);
        context.redirect("/app/comunidades");
    }

    public void unirseAComunidad(Context context) {
        Usuario usuario = super.usuarioLogueado(context);
        Persona persona = this.personaRepository.getPersonaByUsuario(usuario);
        Comunidad comunidad = (Comunidad) this.comunidadRepository.findById(Long.parseLong(context.pathParam("id")));
        comunidad.agregarNuevoMiembro(persona, true);
        comunidadRepository.update(comunidad);
        context.redirect("/app/comunidades");
    }

    @Override
    public void show(Context context) {
        Comunidad comunidad = (Comunidad) this.comunidadRepository.findById(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("comunidad", comunidad);
        context.render("comunidades/comunidades.hbs", model);
    }

    @Override
    public void create(Context context) {
        Comunidad comunidad = null;
        Map<String, Object> model = new HashMap<>();
        model.put("comunidad", comunidad);
        context.render("comunidades/comunidad-create.hbs", model);
    }

    @Override
    public void save(Context context) {
        Comunidad comunidad = this.asignarParametros(null, context);
        this.comunidadRepository.save(comunidad);
        context.status(HttpStatus.CREATED);
        context.redirect("/app/admin-plataforma/comunidades");
    }
    @Override
    public void edit(Context context) {
        Comunidad comunidad = (Comunidad) this.comunidadRepository.findById(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        List<Miembro> miembros = comunidad.getMiembros();
        List<Miembro> administradores = comunidad.getAdministradores();
        model.put("miembros", miembros);
        model.put("administradores", administradores);
        model.put("comunidad", comunidad);
        context.render("comunidades/comunidad-edit.hbs", model);
    }

    @Override
    public void update(Context context) {
        Comunidad comunidad = (Comunidad) this.comunidadRepository.findById(Long.parseLong(context.pathParam("id")));
        this.asignarParametros(comunidad, context);
        this.comunidadRepository.update(comunidad);
        context.redirect("/app/admin-plataforma/comunidades");
    }

    @Override
    public void delete(Context context){
        Long idComunidad = Long.parseLong(context.pathParam("id"));
        Comunidad comunidad = (Comunidad) this.comunidadRepository.findById(idComunidad);
        List<Long> miembroIds = comunidad.getMiembros().stream().map(Miembro::getId).toList();
        miembroRepository.deleteAll(miembroIds);
        this.comunidadRepository.delete(comunidad);
        context.redirect("/app/admin-plataforma/comunidades");
    }

    private Comunidad asignarParametros(Comunidad comunidad, Context context) {
        String nombre = context.formParam("nombre");

        if(comunidad == null) {
            comunidad = new Comunidad(nombre);
        } else {
            comunidad.setNombre(nombre);
        }
        return comunidad;
    }
}
