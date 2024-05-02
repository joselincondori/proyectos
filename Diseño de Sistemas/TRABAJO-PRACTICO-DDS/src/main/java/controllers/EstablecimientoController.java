package controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import models.entities.domain.entidades.*;
import models.entities.domain.georef.Ubicacion;
import models.repositories.*;
import server.utils.ICrudViewsHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EstablecimientoController  implements ICrudViewsHandler {
    private EntidadRepository entidadRepository;
    private EstablecimientoRepository establecimientoRepository;
    private TipoEstablecimientoRepository tipoEstablecimientoRepository;
    private UbicacionRepository ubicacionRepository;

    public EstablecimientoController() {
        this.entidadRepository = new EntidadRepository();
        this.establecimientoRepository = new EstablecimientoRepository();
        this.tipoEstablecimientoRepository = new TipoEstablecimientoRepository();
        this.ubicacionRepository = new UbicacionRepository();
    }

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<Establecimiento> establecimientos = this.establecimientoRepository.findAll();
        model.put("establecimientos", establecimientos);
        context.render("admin-plataforma/admin-establecimientos.hbs", model);
    }

    @Override
    public void show(Context context) {
        Establecimiento establecimiento = (Establecimiento) this.establecimientoRepository.findById(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("establecimiento", establecimiento);
        context.render("establecimientos/establecimiento.hbs", model);
    }

    @Override
    public void create(Context context) {
        Establecimiento establecimiento = null;
        Long entidadId = Long.parseLong(context.pathParam("id"));
        Entidad entidad = (Entidad) entidadRepository.findById(entidadId);
        List<TipoEstablecimiento> tiposEstablecimiento = tipoEstablecimientoRepository.findAll();
        Map<String, Object> model = new HashMap<>();
        model.put("establecmiento", establecimiento);
        model.put("entidad", entidad);
        model.put("tiposEstablecimiento", tiposEstablecimiento);
        context.render("establecimientos/establecimiento_crear_editar.hbs", model);
    }

    @Override
    public void save(Context context) {
       try {
           Establecimiento establecimiento = asignarParametros(null, context);
           this.establecimientoRepository.save(establecimiento);
           context.status(HttpStatus.CREATED);
           context.redirect("/app/entidades/" + establecimiento.getEntidad().getId() + "/establecimientos/edit");
       } catch (Exception e) {
           e.printStackTrace();
           context.status(HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @Override
    public void edit(Context context) {
        Long establecimientoId = Long.parseLong(context.pathParam("id"));
        Establecimiento establecimiento = (Establecimiento) this.establecimientoRepository.findById(establecimientoId);
        List<TipoEstablecimiento> tiposEstablecimiento = tipoEstablecimientoRepository.findAll();
        tiposEstablecimiento.remove(establecimiento.getTipo());
        Map<String, Object> model = new HashMap<>();
        model.put("establecimiento", establecimiento);
        model.put("tiposEstablecimiento", tiposEstablecimiento);
        context.render("establecimientos/establecimiento_crear_editar.hbs", model);
    }

    @Override
    public void update(Context context) {
        Establecimiento establecimiento = (Establecimiento) this.establecimientoRepository.findById(Long.parseLong(context.pathParam("id")));
        establecimiento = asignarParametros(establecimiento, context);
        establecimientoRepository.update(establecimiento);
        context.redirect("/app/entidades/" + establecimiento.getEntidad().getId() + "/establecimientos/edit");
    }

    @Override
    public void delete(Context context) {
        Establecimiento establecimiento = (Establecimiento) this.establecimientoRepository.findById(Long.parseLong(context.pathParam("id")));
        Long entidadId = establecimiento.getEntidad().getId();
        this.establecimientoRepository.delete(establecimiento);
        context.redirect("/app/entidades/" + entidadId + "/establecimientos/edit");
    }

    private Establecimiento asignarParametros(Establecimiento establecimiento, Context context) {
        String nombre = context.formParam("nombre");

        TipoEstablecimiento tipoEstablecimiento = null;
        Long tipoEestablecimientoId = Long.parseLong(context.formParam("tipoEstablecimiento"));
        if(establecimiento == null || !Objects.equals(tipoEestablecimientoId, establecimiento.getTipo().getId())) {
            tipoEstablecimiento = (TipoEstablecimiento) tipoEstablecimientoRepository.findById(tipoEestablecimientoId);
        }

        double lat = Double.parseDouble(context.formParam("latitud"));
        double lng = Double.parseDouble(context.formParam("longitud"));
        Ubicacion ubicacion = new Ubicacion(lat, lng);


        if(establecimiento == null) {
            Long entidadId = Long.parseLong(context.formParam("entidadId"));
            Entidad entidad = (Entidad) entidadRepository.findById(entidadId);
            establecimiento = new Establecimiento(nombre, ubicacion, tipoEstablecimiento, entidad);
        } else {
            establecimiento.setNombre(nombre);

            if(tipoEstablecimiento != null)
                establecimiento.setTipo(tipoEstablecimiento);

            establecimiento.setUbicacion(ubicacion);
        }
        return establecimiento;
    }
}
