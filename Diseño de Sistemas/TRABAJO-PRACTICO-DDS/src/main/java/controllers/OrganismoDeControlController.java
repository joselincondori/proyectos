package controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.UploadedFile;
import models.entities.domain.entidades.EntidadPrestadora;
import models.entities.domain.entidades.OrganismoDeControl;
import models.entities.domain.persona.Persona;
import models.entities.domain.servicio.Servicio;
import models.entities.importadores.entidadesPrestadoras.ArchivoEnProceso;
import models.entities.importadores.entidadesPrestadoras.EstadoEnProceso;
import models.entities.importadores.entidadesPrestadoras.ImportadorCSV;
import models.entities.sesion.Usuario;
import models.repositories.OrganismoDeControlRepository;
import models.repositories.PersonaRepository;
import models.repositories.ServicioRepository;
import models.repositories.UsuarioRepository;
import org.jetbrains.annotations.NotNull;
import server.utils.ICrudViewsHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.util.*;
import java.util.concurrent.*;

public class OrganismoDeControlController implements ICrudViewsHandler {
    private OrganismoDeControlRepository repository;
    private PersonaRepository repositoryPersona;
    private UsuarioRepository repositoryUsuario;
    private ServicioRepository servicioRepository;
    private List<ArchivoEnProceso> archivosEnProceso = new ArrayList<>();
    public OrganismoDeControlController() {
        this.repository = new OrganismoDeControlRepository();
        this.repositoryPersona = new PersonaRepository();
        this.repositoryUsuario = new UsuarioRepository();
        this.servicioRepository = new ServicioRepository();
    }

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<OrganismoDeControl> organismos = this.repository.findAll();
        model.put("organismos", organismos);
        context.render("admin-plataforma/admin-organismos.hbs", model);
    }

    public void cargaMasiva(Context context) {
        context.render("organismos/carga-masiva.hbs");
    }

    private void actualizarEstadoArchivoEnProceso(String nombreArchivo, EstadoEnProceso estado) {
        for (ArchivoEnProceso archivo : archivosEnProceso) {
            if (archivo.getNombre().equals(nombreArchivo)) {
                archivo.setEstado(estado);
                return;
            }
        }
    }

    public void procesarCargaMasiva(Context context) {
        try {
            UploadedFile uploadedFile = context.uploadedFile("archivoCSV");

            if (uploadedFile != null) {
                File archivoTemporal = File.createTempFile("tempCSV", ".csv");

                String nombreArchivo = context.formParam("nombreArchivo");
                ArchivoEnProceso archivo = new ArchivoEnProceso(nombreArchivo, EstadoEnProceso.ESPERA);
                archivosEnProceso.add(archivo);

                try (InputStream inputStream = uploadedFile.content();
                     FileOutputStream outputStream = new FileOutputStream(archivoTemporal)) {
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                String archivoCSVPath = archivoTemporal.getAbsolutePath();
                ImportadorCSV importadorCSV = new ImportadorCSV();

                List<OrganismoDeControl> organismosDeControl = importadorCSV.importarOrganismosDeControl(archivoCSVPath);

                for (OrganismoDeControl organismoDeControl : organismosDeControl) {
                    this.repositoryUsuario.save(organismoDeControl.getRepresentante().getUsuario());
                    this.repositoryPersona.save(organismoDeControl.getRepresentante());
                    this.repository.save(organismoDeControl);
                }
                actualizarEstadoArchivoEnProceso(nombreArchivo, EstadoEnProceso.EXITO);

                Map<String, Object> model = new HashMap<>();
                model.put("archivosEnProceso", archivosEnProceso);
                context.render("organismos/carga-masiva.hbs", model);
            } else {
                context.status(HttpStatus.BAD_REQUEST);
                context.result("Debe seleccionar un archivo CSV válido.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            context.status(HttpStatus.INTERNAL_SERVER_ERROR);
            context.result("Error al procesar el archivo CSV.");
        }

    }

    @Override
    public void show(Context context) {
        OrganismoDeControl organismo = (OrganismoDeControl) this.repository.findById(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("organismo", organismo);
        context.render("organismos/organismo.hbs", model);
    }

    @Override
    public void create(Context context) {
        OrganismoDeControl organismoDeControl = null;
        Map<String, Object> model = new HashMap<>();
        model.put("organismo", organismoDeControl);
        context.render("organismos/organismo-create.hbs", model);
    }

    @Override
    public void save(Context context) {
        try {
            OrganismoDeControl organismoDeControl = new OrganismoDeControl();
            Persona representante = new Persona();
            Usuario usuario = new Usuario();

            this.asignarParametros(organismoDeControl, representante, usuario, context);

            representante.setUsuario(usuario);
            organismoDeControl.setRepresentante(representante);

            this.repositoryUsuario.save(usuario);
            this.repositoryPersona.save(representante);
            this.repository.save(organismoDeControl);
            context.status(HttpStatus.CREATED);
            context.redirect("/app/organismos");

        } catch (Exception e) {
            e.printStackTrace();
            context.status(HttpStatus.INTERNAL_SERVER_ERROR);
            context.result("Error al crear el OrganismoDeControl.");
        }
    }

    @Override
    public void edit(Context context) {
        OrganismoDeControl organismoDeControl = (OrganismoDeControl) this.repository.findById(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("organismo", organismoDeControl);
        context.render("organismos/organismo-edit.hbs", model);
    }

    @Override
    public void update(Context context) {
        OrganismoDeControl organismoDeControl = (OrganismoDeControl) this.repository.findById(Long.parseLong(context.pathParam("id")));
        Persona representante = (Persona) this.repositoryPersona.findById(Long.parseLong(context.pathParam("idPersona")));
        Usuario usuario = (Usuario) this.repositoryUsuario.findById(Long.parseLong(context.pathParam("idUsuario")));

        this.asignarParametros(organismoDeControl, representante, usuario, context);
        this.repositoryUsuario.update(usuario);
        this.repositoryPersona.update(representante);
        this.repository.update(organismoDeControl);
        context.redirect("/app/organismos");
    }

    @Override
    public void delete(Context context) {
        OrganismoDeControl organismoDeControl = (OrganismoDeControl) this.repository.findById(Long.parseLong(context.pathParam("id")));
        this.repository.delete(organismoDeControl);
        context.redirect("/app/admin-plataforma/organismos");
    }

    public void editServicios(Context context){
        Map<String, Object> model = new HashMap<>();
        OrganismoDeControl organismoDeControl = (OrganismoDeControl) this.repository.findById(Long.parseLong(context.pathParam("id")));
        List<Servicio> servicios = organismoDeControl.getServiciosQueControla();
        model.put("organismo", organismoDeControl);
        model.put("servicios", servicios);
        context.render("organismos/organismos-servicios/edit-servicios.hbs", model);
    }

    public void agregarServicioView(Context context) {
        Map<String, Object> model = new HashMap<>();
        Long organismoId = Long.parseLong(context.pathParam("id"));
        OrganismoDeControl organismoDeControl = (OrganismoDeControl) this.repository.findById(organismoId);
        List<Servicio> todosLosServiciosNoControlados = this.servicioRepository.findServiciosNoControlados();

        model.put("servicios", todosLosServiciosNoControlados);
        model.put("organismo", organismoDeControl);
        context.render("servicios/agregar_servicio_organismo.hbs", model);
    }

    private void asignarParametros(OrganismoDeControl organismoDeControl, Persona representante, Usuario usuario, Context context) {
        if(!Objects.equals(context.formParam("nombre"), "")) {
            organismoDeControl.setNombre(context.formParam("nombre"));
        }
        if(!Objects.equals(context.formParam("nombreRepresentante"), "")){
            representante.setNombre(context.formParam("nombreRepresentante"));
        }
        if(!Objects.equals(context.formParam("mailRepresentante"), "")){
            representante.setMail(context.formParam("mailRepresentante"));
        }
        if(!Objects.equals(context.formParam("apellidoRepresentante"), "")){
            representante.setApellido(context.formParam("apellidoRepresentante"));
        }
        if(!Objects.equals(context.formParam("telefonoRepresentante"), "")){
            representante.setTelefono(context.formParam("telefonoRepresentante"));
        }
        if(!Objects.equals(context.formParam("usuario"), "")){
            usuario.setUsername(context.formParam("usuario"));
        }
    }
}

