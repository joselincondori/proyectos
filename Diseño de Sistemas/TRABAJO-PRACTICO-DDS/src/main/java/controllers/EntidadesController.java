package controllers;

import controllers.dtos.EstablecimientoDto;
import controllers.dtos.PrestacionServicioDto;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import models.entities.domain.entidades.*;
import models.entities.domain.georef.Localidad;
import models.entities.domain.georef.Municipio;
import models.entities.domain.georef.Provincia;
import models.entities.domain.persona.Persona;
import models.entities.domain.servicio.PrestacionServicio;
import models.entities.domain.servicio.Servicio;
import models.entities.sesion.Usuario;
import models.repositories.*;
import server.utils.ICrudViewsHandler;

import javax.persistence.NoResultException;
import java.util.*;

public class EntidadesController extends Controller implements ICrudViewsHandler {
    private final PrestacionServicioRepository prestacionServicioRepository;
    private final EntidadRepository entidadRepository;
    private EntidadPrestadoraRepository entidadPrestadoraRepository;
    private ServicioRepository servicioRepository;
    private EstablecimientoRepository establecimientoRepository;
    private TipoEntidadRepository tipoEntidadRepository;
    private LocalidadRepository localidadRepository;
    private MunicipioRepository municipioRepository;
    private ProvinciaRepository provinciaRepository;
    private PersonaRepository personaRepository;

    public EntidadesController() {
        prestacionServicioRepository = new PrestacionServicioRepository();
        entidadRepository = new EntidadRepository();
        this.servicioRepository = new ServicioRepository();
        entidadPrestadoraRepository = new EntidadPrestadoraRepository();
        localidadRepository = new LocalidadRepository();
        provinciaRepository = new ProvinciaRepository();
        municipioRepository = new MunicipioRepository();
        tipoEntidadRepository = new TipoEntidadRepository();
        establecimientoRepository = new EstablecimientoRepository();
        personaRepository = new PersonaRepository();
    }

    @Override
    public void index(Context context){
        Map<String, Object> model = new HashMap<>();
        List<Entidad> entidades = this.entidadRepository.findAll();
        model.put("entidades", entidades);
        context.render("admin-plataforma/admin-entidades.hbs", model);
    }

    public void entidadesEntidadPrestadora(Context context) {
        EntidadPrestadora entidadPrestadora = (EntidadPrestadora) this.entidadPrestadoraRepository.findById(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("entidad-prestadora", entidadPrestadora);
        model.put("entidades", entidadPrestadora.getEntidadesQueControla());
        context.render("entidades-prestadoras/entidad-prestadora-entidades/entidades-entidad-prestadora.hbs", model);
    }

    @Override
    public void show(Context context) {
        Entidad entidad = (Entidad) this.entidadRepository.findById(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("entidad", entidad);
        context.render("entidades/entidades-entidad-prestadora-create.hbs", model);
    }

    @Override
    public void create(Context context) {
        List<Provincia> provincias = provinciaRepository.findAll();
        List<TipoEntidad> tiposEntidad = tipoEntidadRepository.findAll();
        Map<String, Object> model = new HashMap<>();
        model.put("entidad", null);
        model.put("provincias", provincias);
        model.put("tiposEntidad", tiposEntidad);
        context.render("entidades/entidad_crear_editar.hbs", model);
    }

    @Override
    public void save(Context context) {
        try {
            Entidad entidad = asignarParametros(null, context);
            this.entidadRepository.save(entidad);

            context.status(HttpStatus.CREATED);
            context.redirect("/app/admin-plataforma/entidades");
        } catch (Exception e) {
            e.printStackTrace();
            context.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void edit(Context context) {
        Entidad entidad = (Entidad) this.entidadRepository.findById(Long.parseLong(context.pathParam("id")));
        List<Provincia> provincias = provinciaRepository.findAll();
        if(entidad.getProvincia() != null)
            provincias.remove(entidad.getProvincia());

        List<TipoEntidad> tiposEntidad = tipoEntidadRepository.findAll();
        tiposEntidad.remove(entidad.getTipo());

        Map<String, Object> model = new HashMap<>();
        model.put("entidad", entidad);
        model.put("provincias", provincias);
        model.put("tiposEntidad", tiposEntidad);
        context.render("entidades/entidad_crear_editar.hbs", model);
    }

    public void editEstablecimientos(Context context){
        Map<String, Object> model = new HashMap<>();
        Entidad entidad = (Entidad) this.entidadRepository.findById(Long.parseLong(context.pathParam("id")));
        if(entidad != null){
            List<Establecimiento> establecimientos = entidad.getEstablecimientos();
            model.put("establecimientos", establecimientos);
        }
        model.put("entidad", entidad);
        context.render("entidades/entidades-establecimientos/entidades-establecimientos.hbs", model);
    }

    @Override
    public void update(Context context) {
        Entidad entidad = (Entidad) this.entidadRepository.findById(Long.parseLong(context.pathParam("id")));
        entidad = asignarParametros(entidad, context);
        this.entidadRepository.update(entidad);
        context.redirect("/app/admin-plataforma/entidades");
    }

    @Override
    public void delete(Context context) {
        Long idEntidad = Long.parseLong(context.pathParam("id"));
        Entidad entidad = (Entidad) this.entidadRepository.findById(idEntidad);
        for (Establecimiento establecimiento : entidad.getEstablecimientos())
            establecimientoRepository.delete(establecimiento);
        this.entidadRepository.delete(entidad);
        context.redirect("/app/entidades");
    }

    public void getEstablecimientosByEntidadId(Context context) {
        try {
            Long entidadId = Long.parseLong(context.pathParam("id"));
            List<Establecimiento> establecimientos = establecimientoRepository.getEstablecimientosDeEntidadByEntidadId(entidadId);
            List<EstablecimientoDto> dtos = new ArrayList<>();

            for(Establecimiento establecimiento : establecimientos) {
                EstablecimientoDto establecimientoDto = new EstablecimientoDto(establecimiento.getId(), establecimiento.getNombre());
                dtos.add(establecimientoDto);
            }
            context.status(200);
            context.json(dtos);

        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            context.status(404);
            context.result("Algo salio mal");
        }
    }

    public void getPrestacionesDeEstablecimientoByEstablecimientoId(Context context) {
        try {
            Long establecimientoId = Long.parseLong(context.pathParam("id"));
            List<PrestacionServicio> prestacionesServicio = prestacionServicioRepository.getPrestacionesByEstablecimientoId(establecimientoId);
            List<PrestacionServicioDto> dtos = new ArrayList<>();

            for(PrestacionServicio prestacion : prestacionesServicio) {
                PrestacionServicioDto prestacionServicioDto = new PrestacionServicioDto(prestacion.getId(), prestacion.getServicio().getNombre(), prestacion.getDescripcion());
                dtos.add(prestacionServicioDto);
            }
            context.status(200);
            context.json(dtos);

        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            context.status(404);
            context.result("Algo salio mal");
        }
    }

    private Entidad asignarParametros(Entidad entidad, Context context) {
        String nombre = context.formParam("nombre");
        Long tipoEntidadId = Long.parseLong(context.formParam("tipoEntidad"));

        TipoEntidad tipoEntidad = null;
        if(entidad == null || !Objects.equals(tipoEntidadId, entidad.getTipo().getId())) {
            tipoEntidad = (TipoEntidad) tipoEntidadRepository.findById(tipoEntidadId);
        }

        Provincia provincia = null;
        if(context.formParam("provincia") != null) {
            Long provinciaId = Long.parseLong(context.formParam("provincia"));
            if (entidad == null || (entidad.getProvincia() != null && !Objects.equals(provinciaId, entidad.getProvincia().getId())))
                provincia = (Provincia) provinciaRepository.findById(provinciaId);
            else
                provincia = entidad.getProvincia();
        }

        Municipio municipio = null;
        if(context.formParam("municipio") != null && !Objects.equals(context.formParam("municipio"), "")) {
            Long municipioId = Long.parseLong(context.formParam("municipio"));
            if(entidad == null || (entidad.getMunicipio() != null && !Objects.equals(municipioId, entidad.getMunicipio().getId())))
                municipio = (Municipio) municipioRepository.findById(municipioId);
            else
                municipio = entidad.getMunicipio();
        }

        Localidad localidad = null;
        if(context.formParam("localidad") != null && !Objects.equals(context.formParam("localidad"), "")) {
            Long localidadId = Long.parseLong(context.formParam("localidad"));
            if(entidad == null || (entidad.getLocalidad() != null && !Objects.equals(localidadId, entidad.getLocalidad().getId())))
                localidad = (Localidad) localidadRepository.findById(localidadId);
            else
                localidad = entidad.getLocalidad();
        }

        if(entidad == null) {
            entidad = new Entidad(nombre, tipoEntidad, localidad, municipio, provincia);
        } else {
            entidad.setNombre(nombre);

            if(tipoEntidad != null) {
                entidad.setTipo(tipoEntidad);
            }

            if(localidad != null) {
                entidad.setLocalidad(localidad);
            }

            if(municipio != null) {
                entidad.setMunicipio(municipio);
            }

            if(provincia != null) {
                entidad.setProvincia(provincia);
            }
        }
        return entidad;
    }
    public void eliminarEntidadDeLaEntidadPrestadora(Context context) {
        Long entidadId = Long.parseLong(context.formParam("entidadId"));
        Long entidadPrestadoraId = Long.parseLong(context.formParam("entidadPrestadoraId"));
        Entidad entidad = (Entidad) this.entidadRepository.findById(entidadId);
        EntidadPrestadora entidadPrestadora = (EntidadPrestadora) this.entidadPrestadoraRepository.findById(entidadPrestadoraId);
        entidadPrestadora.getEntidadesQueControla().remove(entidad);
        this.entidadPrestadoraRepository.update(entidadPrestadora);
        context.redirect("/app/admin-plataforma/entidades-prestadoras");
    }
    public void agregarEntidadAEntidadPrestadora(Context context) {
        Long entidadId = Long.parseLong(context.formParam("entidad"));
        Long entidadPrestadoraId = Long.parseLong(context.formParam("entidadPrestadora"));
        EntidadPrestadora entidadPrestadora = (EntidadPrestadora) entidadPrestadoraRepository.findById(entidadPrestadoraId);
        Entidad entidad = (Entidad) entidadRepository.findById(entidadId);
        entidadPrestadora.agregarEntidadesQueControlan(entidad);

        entidadPrestadoraRepository.update(entidadPrestadora);
        context.redirect("/app/admin-plataforma/entidades-prestadoras");
    }
    public void agregarEntidadAUsuario(Context context) {
        Long entidadId = Long.parseLong(context.formParam("entidad"));
        Usuario usuario = super.usuarioLogueado(context);
        Entidad entidad = (Entidad) entidadRepository.findById(entidadId);
        Persona persona = personaRepository.getPersonaByUsuario(usuario);

        persona.getInteres().agregarInteres(entidad);
        entidadRepository.update(entidad);
        personaRepository.update(persona);

        context.redirect("/app/intereses");
    }
}
