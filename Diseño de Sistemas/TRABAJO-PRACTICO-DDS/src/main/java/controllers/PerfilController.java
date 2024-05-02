package controllers;

import io.javalin.http.Context;
import models.entities.domain.entidades.Entidad;
import models.entities.domain.entidades.OrganismoDeControl;
import models.entities.domain.georef.Localidad;
import models.entities.domain.georef.Municipio;
import models.entities.domain.georef.Provincia;
import models.entities.domain.notificaciones.estrategia.Email.EstrategiaEmail;
import models.entities.domain.notificaciones.estrategia.Whatsapp.EstrategiaWpp;
import models.entities.domain.notificaciones.tiempo.Inmediata;
import models.entities.domain.notificaciones.tiempo.SinApuro;
import models.entities.domain.persona.ConfigNotificacion;
import models.entities.domain.persona.Persona;
import models.entities.domain.servicio.Servicio;
import models.entities.sesion.Usuario;
import models.repositories.*;
import server.utils.ICrudViewsHandler;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerfilController extends Controller{
    PersonaRepository personaRepository;
    UsuarioRepository usuarioRepository;
    SinApuroRepository sinApuroRepository;
    TiempoNotificacionRepository tiempoNotificacionRepository;
    ConfigNotificacionRepository configNotificacionRepository;
    ProvinciaRepository provinciaRepository;
    MunicipioRepository municipioRepository;
    LocalidadRepository localidadRepository;
    ServicioRepository servicioRepository;
    EntidadRepository entidadRepository;

    public PerfilController(){
        this.personaRepository = new PersonaRepository();
        this.usuarioRepository = new UsuarioRepository();
        this.sinApuroRepository = new SinApuroRepository();
        this.tiempoNotificacionRepository = new TiempoNotificacionRepository();
        this.configNotificacionRepository = new ConfigNotificacionRepository();
        this.provinciaRepository = new ProvinciaRepository();
        this.municipioRepository = new MunicipioRepository();
        this.localidadRepository = new LocalidadRepository();
        this.servicioRepository = new ServicioRepository();
        this.entidadRepository = new EntidadRepository();
    }

    public void perfil(Context context) {
        Map<String, Object> model = new HashMap<>();
        Usuario usuario = super.usuarioLogueado(context);
        Persona persona = personaRepository.getPersonaByUsuario(usuario);
        List<Provincia> provincias = provinciaRepository.findAll();
        model.put("usuario", usuario);
        model.put("persona", persona);
        if(persona.getConfigNotificacion().getTiempoNotificacion() != null && (persona.getConfigNotificacion().getTiempoNotificacion()) instanceof SinApuro) {
            model.put("horarioActual", ((SinApuro) persona.getConfigNotificacion().getTiempoNotificacion()).getHorarios().get(0));
        }


        if(persona.getProvincia() != null) {
            Provincia provinciaActual = persona.getProvincia();
            model.put("provinciaActual", provinciaActual);
            provincias.remove(provinciaActual);
        }

        model.put("provincias", provincias);
        context.render("perfil.hbs", model);
    }

    public void intereses(Context context) {
        Map<String, Object> model = new HashMap<>();
        Usuario usuario = super.usuarioLogueado(context);
        Persona persona = personaRepository.getPersonaByUsuario(usuario);
        List<Entidad> entidadesInteres = persona.getInteres().getEntidades();
        List<Servicio> serviciosInteres = persona.getInteres().getServicios();
        model.put("entidadesInteres", entidadesInteres);
        model.put("serviciosInteres", serviciosInteres);
        model.put("usuario", usuario.getUsername());
        context.render("intereses/intereses.hbs", model);
    }
    public void agregarServicioView(Context context) {
        Map<String, Object> model = new HashMap<>();
        Usuario usuario = super.usuarioLogueado(context);
        Persona persona = personaRepository.getPersonaByUsuario(usuario);
        List<Servicio> todosLosServiciosNoInteresados = this.servicioRepository.findServiciosNoInteresados(persona.getId());

        model.put("servicios", todosLosServiciosNoInteresados);
        context.render("intereses/agregar-servicio-interes.hbs", model);
    }
    public void agregarEntidadView(Context context) {
        Map<String, Object> model = new HashMap<>();
        Usuario usuario = super.usuarioLogueado(context);
        Persona persona = personaRepository.getPersonaByUsuario(usuario);
        List<Entidad> todasLasEntidadesNoInteresadas = this.entidadRepository.findEntidadesNoInteresadas(persona.getId());

        model.put("entidades", todasLasEntidadesNoInteresadas);
        context.render("intereses/agregar-entidad-interes.hbs", model);
    }

    public void guardarCambios(Context context) {
        Usuario usuario = super.usuarioLogueado(context);
        Persona persona = personaRepository.getPersonaByUsuario(usuario);
        String nombre = context.formParam("nombre");
        String apellido = context.formParam("apellido");
        String mail = context.formParam("mail");
        String telefono = context.formParam("telefono");
        String medio_notificacion = context.formParam("medio_notificacion");
        String tiempo_notificacion = context.formParam("tiempo_notificacion");
        String horario_notificacion = context.formParam("hora_noti");
        String provinciaId = context.formParam("provincia");
        String municipioId = context.formParam("municipio");
        String localidadId = context.formParam("localidad");

        Provincia provincia = (Provincia) provinciaRepository.findById(Long.parseLong(provinciaId));
        Municipio municipio = (Municipio) municipioRepository.findById(Long.parseLong(municipioId));
        Localidad localidad = (Localidad) localidadRepository.findById(Long.parseLong(localidadId));

        if (nombre != null && !nombre.isEmpty()) {
            persona.setNombre(nombre);
        }
        if (apellido != null && !apellido.isEmpty()) {
            persona.setApellido(apellido);
        }
        if (mail != null && !mail.isEmpty()) {
            persona.setMail(mail);
        }
        if (telefono != null && !telefono.isEmpty()) {
            persona.setTelefono(telefono);
        }

        if (medio_notificacion != null && !medio_notificacion.isEmpty()) {
            Integer medio_notificacion_id = Integer.valueOf(medio_notificacion);
            if (medio_notificacion_id == 1) {
                EstrategiaEmail estrategiaEmail = new EstrategiaEmail();
                persona.getConfigNotificacion().setEstrategiaNotificacion(estrategiaEmail);
            }
            if (medio_notificacion_id == 2) {
                EstrategiaWpp estrategiaWpp = new EstrategiaWpp();
                persona.getConfigNotificacion().setEstrategiaNotificacion(estrategiaWpp);
            }
        }

        if (tiempo_notificacion != null && !tiempo_notificacion.isEmpty()) {
            Integer tiempo_notificacion_id = Integer.valueOf(tiempo_notificacion);
            if (tiempo_notificacion_id == 1) {
                Inmediata inmediata = new Inmediata();
                persona.getConfigNotificacion().setTiempoNotificacion(inmediata);
            }
            if (tiempo_notificacion_id == 2) {
                System.out.println("LLEGUE ADENTRO DEL 1ER IF");
                System.out.println("Horario Notificacion " + horario_notificacion);
                if (horario_notificacion != null && !horario_notificacion.isEmpty()) {
                    System.out.println("LLEGUE ADENTRO DEL 2DO IF");
                    LocalTime horario = LocalTime.parse(horario_notificacion);
                    SinApuro sinApuro = new SinApuro();
                    sinApuro.setNotificacionProgramada(null); //TODO cuando se implementen las notificaciones programadas, hay que cambiar esto
                    sinApuro.agregarHorariosEnvio(horario);
                    persona.getConfigNotificacion().setTiempoNotificacion(sinApuro);
                    sinApuroRepository.save(sinApuro);
                }
            }
        }

        if (!provinciaId.isEmpty()) {
            persona.setProvincia(provincia);
        }
        if (!municipioId.isEmpty()) {
            persona.setMunicipio(municipio);
        }
        if (!localidadId.isEmpty()) {
            persona.setLocalidad(localidad);
        }

        tiempoNotificacionRepository.save(persona.getConfigNotificacion().getTiempoNotificacion());
        configNotificacionRepository.update(persona.getConfigNotificacion());
        personaRepository.update(persona);
        usuarioRepository.update(usuario);
        context.redirect("/app/perfil");
    }
}
