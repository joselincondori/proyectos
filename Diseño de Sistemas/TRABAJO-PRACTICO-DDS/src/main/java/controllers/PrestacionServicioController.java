package controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import models.entities.domain.entidades.Entidad;
import models.entities.domain.entidades.Establecimiento;
import models.entities.domain.servicio.PrestacionServicio;
import models.entities.domain.servicio.Servicio;
import models.repositories.EstablecimientoRepository;
import models.repositories.PrestacionServicioRepository;
import models.repositories.ServicioRepository;
import server.utils.ICrudViewsHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PrestacionServicioController implements ICrudViewsHandler {

    private final PrestacionServicioRepository prestacionServicioRepository = new PrestacionServicioRepository();
    private final EstablecimientoRepository establecimientoRepository = new EstablecimientoRepository();
    private final ServicioRepository servicioRepository = new ServicioRepository();


    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {

    }

    @Override
    public void create(Context context) {
        Long establecimientoId = Long.parseLong(context.pathParam("id"));
        Establecimiento establecimiento = (Establecimiento) this.establecimientoRepository.findById(establecimientoId);
        System.out.println(establecimiento.getId());
        List<Servicio> servicios = (List<Servicio>) servicioRepository.findAll();
        Map<String, Object> model = new HashMap<>();
        model.put("establecimiento", establecimiento);
        model.put("servicios", servicios);
        model.put("prestacion", null);
        context.render("prestaciones/prestaciones_crear_editar.hbs", model);
    }

    @Override
    public void save(Context context) {
        try {
            PrestacionServicio prestacion = asignarParametros(null, context);
            prestacionServicioRepository.save(prestacion);
            context.status(HttpStatus.CREATED);
            context.redirect("/app/establecimientos/" + prestacion.getEstablecimiento().getId() + "/prestaciones/edit");
        } catch (Exception e) {
            e.printStackTrace();
            context.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void edit(Context context) {
        Long prestacionId = Long.parseLong(context.pathParam("id"));
        PrestacionServicio prestacionServicio = (PrestacionServicio) prestacionServicioRepository.findById(prestacionId);
        List<Servicio> servicios = servicioRepository.findAll();
        servicios.remove(prestacionServicio.getServicio());
        Map<String, Object> model = new HashMap<>();
        model.put("prestacion", prestacionServicio);
        model.put("servicios", servicios);
        context.render("prestaciones/prestaciones_crear_editar.hbs", model);
    }

    @Override
    public void update(Context context) {
        Long prestacionId = Long.parseLong(context.pathParam("id"));
        PrestacionServicio prestacion = (PrestacionServicio) prestacionServicioRepository.findById(prestacionId);
        prestacion = asignarParametros(prestacion, context);
        prestacionServicioRepository.update(prestacion);
        context.redirect("/app/establecimientos/" + prestacion.getEstablecimiento().getId() + "/prestaciones/edit");
    }

    public void editPrestaciones(Context context){
        Map<String, Object> model = new HashMap<>();
        Establecimiento establecimiento = (Establecimiento) this.establecimientoRepository.findById(Long.parseLong(context.pathParam("id")));
        List<PrestacionServicio> prestaciones = prestacionServicioRepository.getPrestacionesByEstablecimientoId(establecimiento.getId());
        model.put("prestaciones", prestaciones);
        model.put("establecimiento", establecimiento);
        context.render("establecimientos/establecimiento_prestaciones.hbs", model);
    }

    @Override
    public void delete(Context context) {
        Long prestacionId = Long.parseLong(context.formParam("prestacionId"));
        PrestacionServicio prestacionServicio = (PrestacionServicio) this.prestacionServicioRepository.findById(prestacionId);

        delete(prestacionServicio);

        context.redirect("/app/establecimientos/" + prestacionServicio.getEstablecimiento().getId() + "/prestaciones/edit");
    }

    public void delete(PrestacionServicio prestacion) {
        this.prestacionServicioRepository.delete(prestacion);
        IncidentesController incidentesController = new IncidentesController();
        incidentesController.cerrarIncidentesDePrestacion(prestacion);
    }

    private PrestacionServicio asignarParametros(PrestacionServicio prestacion, Context context) {
        String descripcion = context.formParam("descripcion");
        Long servicioId = Long.parseLong(context.formParam("servicio"));
        Servicio servicio = null;
        if(prestacion == null || !Objects.equals(servicioId, prestacion.getServicio().getId()))
            servicio = (Servicio) servicioRepository.findById(servicioId);

        if(prestacion == null) {
            Long establecimientoId = Long.parseLong(context.formParam("establecimientoId"));
            Establecimiento establecimiento = (Establecimiento) establecimientoRepository.findById(establecimientoId);

            prestacion = new PrestacionServicio(descripcion, establecimiento, servicio);
        } else {
            prestacion.setDescripcion(descripcion);

            if(servicio != null)
                prestacion.setServicio(servicio);
        }
        return prestacion;
    }
}
