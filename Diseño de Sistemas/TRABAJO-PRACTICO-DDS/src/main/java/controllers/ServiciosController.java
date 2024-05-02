package controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import models.entities.domain.entidades.EntidadPrestadora;
import models.entities.domain.entidades.OrganismoDeControl;
import models.entities.domain.persona.Persona;
import models.entities.domain.servicio.PrestacionServicio;
import models.entities.domain.servicio.Servicio;
import models.entities.sesion.Usuario;
import models.repositories.OrganismoDeControlRepository;
import models.repositories.PersonaRepository;
import models.repositories.PrestacionServicioRepository;
import models.repositories.ServicioRepository;
import org.jetbrains.annotations.NotNull;
import server.exceptions.AccessDeniedException;
import server.utils.ICrudViewsHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ServiciosController extends Controller implements ICrudViewsHandler{

    private ServicioRepository servicioRepository;
    private OrganismoDeControlRepository organismoDeControlRepository;
    private PrestacionServicioRepository prestacionServicioRepository;
    private PersonaRepository personaRepository;

    public ServiciosController(){
        organismoDeControlRepository = new OrganismoDeControlRepository();
        servicioRepository = new ServicioRepository();
        prestacionServicioRepository = new PrestacionServicioRepository();
        personaRepository = new PersonaRepository();
    }
    @Override
    public void index(Context context){
        Map<String, Object> model = new HashMap<>();
        List<Servicio> servicios = this.servicioRepository.findAll();
        model.put("servicios", servicios);
        context.render("admin-plataforma/admin-servicios.hbs", model);
    }
    public void serviciosDelOrganismo(Context context) {
        OrganismoDeControl organismo = (OrganismoDeControl) this.organismoDeControlRepository.findById(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("organismo", organismo);
        model.put("servicios", organismo.getServiciosQueControla());
        context.render("servicios/servicios-organismo.hbs", model);
    }

    @Override
    public void show(Context context) {
        Servicio servicio = (Servicio) this.servicioRepository.findById(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("servicio", servicio);
        context.render("servicios/servicio.hbs", model);
    }

    @Override
    public void create(Context context) {
        Servicio servicio = null;
        Map<String, Object> model = new HashMap<>();
        model.put("servicio", servicio);
        context.render("servicios/servicio_crear_editar.hbs", model);
    }

    @Override
    public void save(Context context) {
        Servicio servicio = asignarParametros(null, context);
        this.servicioRepository.save(servicio);
        context.status(HttpStatus.CREATED);
        context.redirect("/app/servicios");
    }

    @Override
    public void edit(Context context) {
        Long servicioId = Long.parseLong(context.pathParam("id"));
        Servicio servicio = (Servicio) this.servicioRepository.findById(servicioId);
        Map<String, Object> model = new HashMap<>();
        model.put("servicio", servicio);
        context.render("servicios/servicio_crear_editar.hbs", model);
    }

    @Override
    public void update(Context context) {
        Servicio servicio = (Servicio) this.servicioRepository.findById(Long.parseLong(context.pathParam("id")));
        servicio = asignarParametros(servicio, context);
        this.servicioRepository.update(servicio);
        context.redirect("/app/admin-plataforma/servicios");
    }

    @Override
    public void delete(Context context) {
        Servicio servicio = (Servicio) this.servicioRepository.findById(Long.parseLong(context.pathParam("id")));
        this.servicioRepository.delete(servicio);
        List<PrestacionServicio> prestacionesServicio = this.prestacionServicioRepository.getPrestacionesByServicioId(servicio.getId());

        PrestacionServicioController prestacionController = new PrestacionServicioController();

        for (PrestacionServicio prestacion : prestacionesServicio) {
            prestacionController.delete(prestacion);
        }
        context.redirect("/app/admin-plataforma/servicios");
    }

    private Servicio asignarParametros(Servicio servicio, Context context) {
        String nombre = context.formParam("nombre");
        String descripcion = context.formParam("descripcion");

        if(servicio == null) {
            servicio = new Servicio(nombre, descripcion);
        } else {
            if(!Objects.equals(nombre, servicio.getNombre()))
                servicio.setNombre(nombre);

            if(!Objects.equals(descripcion, servicio.getDescripcion()))
                servicio.setDescripcion(descripcion);
        }
        return servicio;
    }

    public void eliminarServicioDeOrganismo(Context context) {
        Long servicioId = Long.parseLong(context.formParam("servicioId"));
        Servicio servicio = (Servicio) this.servicioRepository.findById(servicioId);
        servicio.setOrganismo(null);
        this.servicioRepository.update(servicio);
        context.redirect("/app/admin-plataforma/organismos");
    }

    public void agregarServicioAOrganismo(Context context) {
        Long servicioId = Long.parseLong(context.formParam("servicio"));
        Long organismoId = Long.parseLong(context.formParam("organismo"));
        OrganismoDeControl organismo = (OrganismoDeControl) organismoDeControlRepository.findById(organismoId);
        Servicio servicio = (Servicio) servicioRepository.findById(servicioId);

        servicio.setOrganismo(organismo);
        servicioRepository.update(servicio);
        context.redirect("/app/admin-plataforma/organismos");
    }

    public void agregarServicioAUsuario(Context context) {
        Long servicioId = Long.parseLong(context.formParam("servicio"));
        Usuario usuario = super.usuarioLogueado(context);
        Servicio servicio = (Servicio) servicioRepository.findById(servicioId);
        Persona persona = personaRepository.getPersonaByUsuario(usuario);

        persona.getInteres().agregarInteres(servicio);
        servicioRepository.update(servicio);
        personaRepository.update(persona);

        context.redirect("/app/intereses");
    }
}
