package controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.UploadedFile;
import models.entities.domain.entidades.Entidad;
import models.entities.domain.entidades.EntidadPrestadora;
import models.entities.domain.entidades.OrganismoDeControl;
import models.entities.domain.persona.Persona;
import models.entities.domain.servicio.Servicio;
import models.entities.importadores.entidadesPrestadoras.ArchivoEnProceso;
import models.entities.importadores.entidadesPrestadoras.EstadoEnProceso;
import models.entities.importadores.entidadesPrestadoras.ImportadorCSV;
import models.entities.sesion.Usuario;
import models.repositories.*;
import server.utils.ICrudViewsHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EntidadPrestadoraController implements ICrudViewsHandler {
    private EntidadPrestadoraRepository repository;
    private OrganismoDeControlRepository organismoDeControlRepository;
    private PersonaRepository repositoryPersona;
    private UsuarioRepository repositoryUsuario;
    private EntidadRepository entidadRepository;
    private List<ArchivoEnProceso> archivosEnProceso = new ArrayList<>();
    public EntidadPrestadoraController() {
        this.repository = new EntidadPrestadoraRepository();
        this.organismoDeControlRepository = new OrganismoDeControlRepository();
        this.repositoryPersona = new PersonaRepository();
        this.repositoryUsuario = new UsuarioRepository();
        this.entidadRepository = new EntidadRepository();
    }

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<EntidadPrestadora> entidades = this.repository.findAll();
        model.put("entidades-prestadoras", entidades);
        context.render("admin-plataforma/admin-entidades-prestadoras.hbs", model);
    }

    public void agregarEntidadesView(Context context) {
        Map<String, Object> model = new HashMap<>();
        Long entidadId = Long.parseLong(context.pathParam("id"));
        EntidadPrestadora entidadPrestadora = (EntidadPrestadora) this.repository.findById(entidadId);
        List<Entidad> entidadesNoControladas = this.entidadRepository.findEntidadesNoControladas();

        model.put("entidades", entidadesNoControladas);
        model.put("entidadPrestadora", entidadPrestadora);
        context.render("entidades-prestadoras/agregar-entidad-entidades.hbs", model);
    }

    @Override
    public void show(Context context) {
        EntidadPrestadora entidadPrestadora = (EntidadPrestadora) this.repository.findById(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("entidadPrestadora", entidadPrestadora);
        context.render("entidades-prestadoras/entidades-prestadoras.hbs", model);
    }

    @Override
    public void create(Context context) {
        EntidadPrestadora entidad = null;
        Map<String, Object> model = new HashMap<>();
        model.put("entidad", entidad);
        context.render("entidades-prestadoras/entidad-prestadora-create.hbs", model);
    }

    public void createEntidadDeOrganismo(Context context) {
        OrganismoDeControl organismo = (OrganismoDeControl) this.organismoDeControlRepository.findById(Long.parseLong(context.pathParam("idOrganismo")));
        EntidadPrestadora entidad = null;

        Map<String, Object> model = new HashMap<>();
        model.put("organismo", organismo);
        model.put("entidad", entidad);
        context.render("organismos/organismos-entidades/entidad-prestadora-create-organismo.hbs", model);
    }
    public void saveEntidadDeOrganismo(Context context){
        try {
            EntidadPrestadora entidadPrestadora = new EntidadPrestadora();
            Persona representante = new Persona();
            Usuario usuario = new Usuario();

            OrganismoDeControl organismo = (OrganismoDeControl) this.organismoDeControlRepository.findById(Long.parseLong(context.pathParam("idOrganismo")));

            this.asignarParametros(entidadPrestadora, representante, usuario, context);

            representante.setUsuario(usuario);
            entidadPrestadora.setRepresentante(representante);

            this.repositoryUsuario.save(usuario);
            this.repositoryPersona.save(representante);
            this.repository.save(entidadPrestadora);
            organismoDeControlRepository.update(organismo);

            context.status(HttpStatus.CREATED);
            context.redirect("/app/organismos");

        } catch (Exception e) {
            e.printStackTrace();
            context.status(HttpStatus.INTERNAL_SERVER_ERROR);
            context.result("Error al crear la Entidad Prestadora.");
        }
        context.redirect("/app/entidades-prestadoras");
    }

    @Override
    public void save(Context context) {
        try {
            EntidadPrestadora entidadPrestadora = new EntidadPrestadora();
            Persona representante = new Persona();
            Usuario usuario = new Usuario();

            this.asignarParametros(entidadPrestadora, representante, usuario, context);

            representante.setUsuario(usuario);
            entidadPrestadora.setRepresentante(representante);

            this.repositoryUsuario.save(usuario);
            this.repositoryPersona.save(representante);
            this.repository.save(entidadPrestadora);
            context.status(HttpStatus.CREATED);
            context.redirect("/app/organismos");

        } catch (Exception e) {
            e.printStackTrace();
            context.status(HttpStatus.INTERNAL_SERVER_ERROR);
            context.result("Error al crear la Entidad Prestadora.");
        }
        context.redirect("/app/entidades-prestadoras");
    }

    @Override
    public void edit(Context context) {
        EntidadPrestadora entidadPrestadora = (EntidadPrestadora) this.repository.findById(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("entidadPrestadora", entidadPrestadora);
        context.render("entidades-prestadoras/entidad-prestadora-edit.hbs", model);
    }

    @Override
    public void update(Context context) {
        EntidadPrestadora entidadPrestadora = (EntidadPrestadora) this.repository.findById(Long.parseLong(context.pathParam("id")));
        Persona representante = (Persona) this.repositoryPersona.findById(Long.parseLong(context.pathParam("idPersona")));
        Usuario usuario = (Usuario) this.repositoryUsuario.findById(Long.parseLong(context.pathParam("idUsuario")));

        this.asignarParametros(entidadPrestadora, representante, usuario, context);
        this.repositoryUsuario.update(usuario);
        this.repositoryPersona.update(representante);
        this.repository.update(entidadPrestadora);
        context.redirect("/app/entidades-prestadoras");
    }

    @Override
    public void delete(Context context) {
        EntidadPrestadora entidadPrestadora = (EntidadPrestadora) this.repository.findById(Long.parseLong(context.pathParam("id")));
        this.repository.delete(entidadPrestadora);
        context.redirect("/app/entidades-prestadoras");
    }

    private void asignarParametros(EntidadPrestadora entidadPrestadora, Persona representante, Usuario usuario, Context context) {
        if(!Objects.equals(context.formParam("nombre"), "")) {
            entidadPrestadora.setNombre(context.formParam("nombre"));
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

    private void actualizarEstadoArchivoEnProceso(String nombreArchivo, EstadoEnProceso estado) {
        for (ArchivoEnProceso archivo : archivosEnProceso) {
            if (archivo.getNombre().equals(nombreArchivo)) {
                archivo.setEstado(estado);
                return;
            }
        }
    }

    public void cargaMasiva(Context context) {
        context.render("entidades-prestadoras/carga-masiva-entidades.hbs");
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

                List<EntidadPrestadora> entidadesPrestadoras = importadorCSV.importarEntidadesPrestadoras(archivoCSVPath);

                for (EntidadPrestadora entidadPrestadora : entidadesPrestadoras) {
                    this.repositoryUsuario.save(entidadPrestadora.getRepresentante().getUsuario());
                    this.repositoryPersona.save(entidadPrestadora.getRepresentante());
                    this.repository.save(entidadPrestadora);
                }
                actualizarEstadoArchivoEnProceso(nombreArchivo, EstadoEnProceso.EXITO);
                Map<String, Object> model = new HashMap<>();
                model.put("archivosEnProceso", archivosEnProceso);
                context.render("entidades-prestadoras/carga-masiva-entidades.hbs", model);
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
}
