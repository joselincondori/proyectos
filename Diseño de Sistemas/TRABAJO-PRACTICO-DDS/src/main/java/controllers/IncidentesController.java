package controllers;

import config.Config;
import io.javalin.http.Context;
import models.entities.domain.comunidad.Miembro;
import models.entities.domain.entidades.Establecimiento;
import models.entities.domain.georef.CalculadorCercania;
import models.entities.domain.georef.Ubicacion;
import models.entities.domain.incidentes.Incidente;

import java.util.*;

import models.entities.domain.comunidad.Comunidad;
import models.entities.domain.entidades.Entidad;
import models.entities.domain.incidentes.SugerenciaRevision;
import models.entities.domain.notificaciones.DatosNotificacion;
import models.entities.domain.notificaciones.Notificador;
import models.entities.domain.notificaciones.tiempo.Inmediata;
import models.entities.domain.persona.Persona;
import models.entities.domain.servicio.PrestacionServicio;
import models.entities.domain.servicio.Servicio;
import models.entities.sesion.Usuario;
import models.repositories.*;
import server.exceptions.AccessDeniedException;

import javax.persistence.NoResultException;

public class IncidentesController extends Controller {
    private final IncidenteRepository incidenteRepository;
    private final ComunidadRepository comunidadRepository;
    private final EntidadRepository entidadRepository;
    private final PrestacionServicioRepository prestacionServicioRepository;
    private final SugerenciaRevisionRepository sugerenciaRevisionRepository;
    private final PersonaRepository personaRepository;
    private final MiembroRepository miembroRepository;

    private final String stackBaseUrl = Config.obtenerInstancia().obtenerDelConfig("stack-base-url");

    public IncidentesController() {
        incidenteRepository = new IncidenteRepository();
        comunidadRepository = new ComunidadRepository();
        entidadRepository = new EntidadRepository();
        prestacionServicioRepository = new PrestacionServicioRepository();
        sugerenciaRevisionRepository = new SugerenciaRevisionRepository();
        personaRepository = new PersonaRepository();
        miembroRepository = new MiembroRepository();
    }

//    public void incidentes(Context context) {
//        Usuario usuario = usuarioLogueado(context);
//        Persona persona = personaRepository.getPersonaByUsuario(usuario);
//        Miembro miembroUser = null;
//        Long comunidadId = Long.parseLong(context.pathParam("id"));
//        Comunidad comunidad = (Comunidad) comunidadRepository.findById(comunidadId);
//        try {
//            miembroUser = miembroRepository.getMiembroByPersonaAndComunidad(persona, comunidad);
//        } catch (NoResultException ignored) {}
//
//        Map<String, Object> model = new HashMap<>();
//
//        List<Incidente> incidentes = aplicarFiltro(context, comunidad);
//        model.put("incidentes", incidentes);
//        model.put("comunidad", comunidad);
//        model.put("miembroUser", miembroUser);
//        context.render("incidentes/incidentes.hbs", model);
//    }

    public void incidentes(Context context) {
        Long comunidadId = Long.parseLong(context.pathParam("id"));
        Comunidad comunidad = (Comunidad) comunidadRepository.findById(comunidadId);

        context.redirect(stackBaseUrl + "app/comunidades/" + comunidad.getId() + "/incidentes");
    }

//    public List<Incidente> aplicarFiltro(Context context, Comunidad comunidad) {
//        List<Incidente> incidentes;
//        String filtro = context.queryParam("filtro");
//
//        if(filtro == null){
//           incidentes = incidenteRepository.getIncidentesDeComunidad(comunidad);
//        } else {
//            switch (filtro) {
//                case "cerrados" -> incidentes = incidenteRepository.getIncidentesCerradosDeComunidad(comunidad);
//                case "abiertos" -> incidentes = incidenteRepository.getIncidentesAbiertosDeComunidad(comunidad);
//                default -> incidentes = incidenteRepository.getIncidentesDeComunidad(comunidad);
//            }
//        }
//        return incidentes;
//    }
    public void requestCerrarIncidente(Context context) {
        Long incidenteId = Long.parseLong(context.pathParam("id"));
        Incidente incidente = (Incidente) incidenteRepository.findById(incidenteId);
        Map<String, Object> model = new HashMap<>();
        model.put("incidente", incidente);
        context.render("incidentes/cerrar.hbs", model);
    }

    public void cerrarIncidente(Context context) {
        String boton = context.formParam("boton");
        Long incidenteId = Long.valueOf(context.formParam("incidenteId"));
        Incidente incidente = (Incidente) incidenteRepository.findById(incidenteId);

        if(Objects.equals(boton, "cerrar")) {
            if(incidente == null || !incidente.estaAbierto()) {
                context.status(400);
                return;
            }

            Usuario usuarioLogueado = usuarioLogueado(context);
            if(usuarioLogueado == null) {
                context.status(401);
                return;
            }
            Persona analizador = personaRepository.getPersonaByUsuario(usuarioLogueado);

            incidente.cerrar(analizador);
            incidenteRepository.update(incidente);

            List<SugerenciaRevision> sugerenciasPendientes = sugerenciaRevisionRepository.getSugerenciasNoResueltasDeIncidente(incidente);
            sugerenciasPendientes.forEach(SugerenciaRevision::cerrarSinResolverIncidente);

//        comunidad.notificarCierreIncidente(incidente); // TODO
        }

        context.redirect(stackBaseUrl + "app/comunidades/" + incidente.getComunidad().getId() + "/incidentes");
    }

    public void abrirIncidenteForm(Context context) {
        Map<String, Object> model = new HashMap<>();

        Usuario usuario = usuarioLogueado(context);
        Persona persona = personaRepository.getPersonaByUsuario(usuario);
        List<Comunidad> comunidades = comunidadRepository.getComunidadesDePersona(persona);
        List<Entidad> entidades = (List<Entidad>) entidadRepository.findAll();

        if(Objects.equals(context.queryParam("error"), "sin-comunidad"))
            model.put("error", "Debe seleccionar al menos una comunidad.");

        model.put("comunidades", comunidades);
        model.put("entidades", entidades);

        context.render("incidentes/abrir_incidente.hbs", model);
    }

    public void abrirIncidente(Context context) {
        List<String> comunidadesStringId = context.formParams("comunidades");
        String prestacionStringId = context.formParam("prestacion");
        String comentarios = context.formParam("comentarios");

        List<Comunidad> comunidades = comunidadesStringId
                .stream()
                .mapToLong(Long::parseLong)
                .mapToObj(id -> (Comunidad) comunidadRepository.findById(id))
                .toList();

        if(comunidades.isEmpty()) {
            context.redirect("/app/incidentes/abrir?error=sin-comunidad");
            return;
        }

        try { // intento traer los datos
            PrestacionServicio prestacionServicio = (PrestacionServicio) prestacionServicioRepository.findById(Long.parseLong(prestacionStringId));

            Persona persona = personaRepository.getPersonaByUsuario(usuarioLogueado(context));

            for (Comunidad comunidad : comunidades) {
                if(!comunidad.personaYaEstaEnComunidad(persona)) //por las dudas chequeo que no haya pasado alguna comunidad a la que no pertenece
                    continue;
                Incidente incidente = new Incidente(comunidad, comentarios, prestacionServicio, persona);
                incidenteRepository.save(incidente);
                //comunidad.notificarAperturaIncidente(incidente); //TODO probar esto
            }

            context.redirect("/app/portal");

        } catch (NoResultException e) {
            e.printStackTrace();
            context.status(500);
        }
    }

    public void sugerenciaRevision(Context context) {
        Long sugerenciaId = Long.parseLong(context.formParam("sugerenciaId"));
        SugerenciaRevision sugerenciaRevision = (SugerenciaRevision) sugerenciaRevisionRepository.findById(sugerenciaId);
        if(sugerenciaRevision == null || sugerenciaRevision.estaResuelta()) {
            context.redirect("/app/portal");
            return;
        } else {
            Usuario usuario = usuarioLogueado(context);
            Usuario usuarioDeSugerencia = sugerenciaRevision.getAnalizador().getUsuario();
            if(usuario != usuarioDeSugerencia) {
                context.redirect("/app/portal");
                return;
            }
        }
        Map<String, Object> model = new HashMap<>();
        Establecimiento establecimiento = sugerenciaRevision.getIncidente().getPrestacionServicio().getEstablecimiento();
        Servicio servicio = sugerenciaRevision.getIncidente().getPrestacionServicio().getServicio();
        Entidad entidad = establecimiento.getEntidad();

        model.put("establecimiento", establecimiento);
        model.put("entidad", entidad);
        model.put("servicio", servicio);
        model.put("sugerenciaId", sugerenciaId);

        context.render("incidentes/sugerencia_revision.hbs", model);
    }

    public void resolverSugerenciaRevisionSegunBoton(Context context) {
        String respuestaBoton = context.formParam("boton");
        Long sugerenciaId = Long.parseLong(context.formParam("sugerenciaId"));
        SugerenciaRevision sugerenciaRevision = (SugerenciaRevision) sugerenciaRevisionRepository.findById(sugerenciaId);
        if(sugerenciaRevision == null) {
            context.redirect("/app/portal");
            return;
        }

        switch(respuestaBoton) {
            case "si": {
                try {
                    Persona analizador = personaRepository.getPersonaByUsuario(usuarioLogueado(context));
                    if(analizador != sugerenciaRevision.getAnalizador())
                        throw new AccessDeniedException();
                    sugerenciaRevision.cerrarYResolverIncidente(analizador);
                } catch (NoResultException e) {
                    e.printStackTrace();
                }
                context.redirect("/app/incidentes/sugerencias/gracias");
                break;
            }
            case "no": sugerenciaRevision.cerrarSinResolverIncidente(); context.redirect("/app/incidentes/sugerencias/gracias"); break;
            case "omitir": sugerenciaRevision.cerrarSinResolverIncidente(); context.redirect("/app/portal"); break;
        }

        sugerenciaRevisionRepository.update(sugerenciaRevision);
    }

    public void mostrarSugerencias(Context context) {
        Map<String, Object> model = new HashMap<>();
        Usuario loggeado = usuarioLogueado(context);
        Persona persona = personaRepository.getPersonaByUsuario(loggeado);
        List<SugerenciaRevision> sugerenciasRevision = sugerenciaRevisionRepository.getSugerenciasPendientesDePersonaById(persona.getId());
        model.put("sugerencias", sugerenciasRevision);
        context.render("incidentes/sugerencias.hbs", model);
    }

    public void sugerenciaRevisionResueltaGracias(Context context) {
        context.render("incidentes/gracias_revision.hbs");
    }

    public void cerrarIncidentesDePrestacion(PrestacionServicio prestacion) {
        List<Incidente> incidentes = this.incidenteRepository.getIncidentesAbiertosDePrestacion(prestacion);
        for(Incidente incidente : incidentes) {
            incidente.cerrar(null);
            this.incidenteRepository.update(incidente);
        }
    }

    public void enviarSugerenciasRevision(Context context) { //TODO Hardcodeada la ubicacion
        List<Comunidad> comunidades = comunidadRepository.findAll();

        for (Comunidad comunidad : comunidades) {
            List<Incidente> incidentes = incidenteRepository.getIncidentesAbiertosDeComunidad(comunidad);
            if (incidentes.isEmpty())
                continue;
            for (Miembro miembro : comunidad.getMiembros()) {
                handleUbicacionMiembro(miembro, incidentes, new Ubicacion(-34, -58));
            }
        }
    }

    public void handleUbicacionMiembro(Miembro miembro, List<Incidente> incidentes, Ubicacion ubiMiembro) {
        for(Incidente incidente : incidentes) {
            Ubicacion ubiIncidente = incidente.getPrestacionServicio().getEstablecimiento().getUbicacion();

            if(CalculadorCercania.getInstance().estaCerca(ubiMiembro, ubiIncidente))
                sugerirRevisionIncidente(miembro, incidente);
        }
    }

    private void sugerirRevisionIncidente(Miembro miembro, Incidente incidente) {
        try {
            sugerenciaRevisionRepository.getSugerenciaAbiertaByPersonaAndIncidente(miembro.getPersona(), incidente);
        } catch (NoResultException e) {
            SugerenciaRevision sugerencia = new SugerenciaRevision(incidente, miembro.getPersona());
            sugerenciaRevisionRepository.save(sugerencia);

            String mensaje = """
                    Hola!
                    Tiene una nueva sugerencia de revision de incidente, podria chequear el estado del servicio por favor?

                    https://grupo12dds.onrender.com/login""";
            String titulo = "Consulta revision de incidente";
            DatosNotificacion datos = miembro.getComunidad().crearDatosNotificacion(titulo, mensaje, new Inmediata(), incidente);
            List<Persona> destinatarios = new ArrayList<>();
            destinatarios.add(miembro.getPersona());
            Notificador.getInstance().notificar(datos, destinatarios);
        }
    }
}
