package controllers;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import models.entities.domain.persona.ConfigNotificacion;
import models.entities.domain.persona.Persona;
import models.entities.domain.roles.Rol;
import models.entities.seguridad.ValidadorDeContrasenia;
import models.entities.seguridad.encriptadores.Encriptador;
import models.entities.seguridad.excepciones.ExcepcionCaracteresInvalidos;
import models.entities.seguridad.excepciones.ExcepcionComun;
import models.entities.sesion.Usuario;
import models.repositories.ConfigNotificacionRepository;
import models.repositories.PersonaRepository;
import models.repositories.UsuarioRepository;
import server.utils.ICrudViewsHandler;
import server.utils.TipoRol;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LogInController implements ICrudViewsHandler {
    private UsuarioRepository usuarioRepository;
    private PersonaRepository personaRepository;
    private ConfigNotificacionRepository configNotificacionRepository;
    private ValidadorDeContrasenia validadorDeContrasenia;

    private Encriptador encriptador;
    public LogInController() {
        this.usuarioRepository = new UsuarioRepository();
        this.personaRepository = new PersonaRepository();
        this.configNotificacionRepository = new ConfigNotificacionRepository();
        this.validadorDeContrasenia = new ValidadorDeContrasenia();
        this.encriptador = new Encriptador();
    }

    //LOGIN
    @Override
    public void index(Context context) {
        context.render("index.hbs");
    }


    public void login(Context context) {
        Map<String, Object> model = new HashMap<>();

        String errorType = context.queryParam("error");
        if (errorType != null) {
            switch (errorType) {
                case "unregistered":
                    model.put("errorMessage", "Usuario no registrado.");
                    break;
                case "encryption_error":
                    model.put("errorMessage", "Error al encriptar la contraseña. Inténtelo nuevamente.");
                    break;
                case "invalid_password":
                    model.put("errorMessage", "Contraseña inválida.");
                    break;
                default:
                    model.put("errorMessage", "Ocurrió un error al intentar iniciar sesión.");
                    break;
            }
        }
        context.render("login.hbs", model);
    }

    public void validar(Context context) throws NoSuchAlgorithmException {
        String nombre = context.formParam("nombre");
        String contrasenia = context.formParam("contrasenia");

        Usuario usuario = (Usuario) this.usuarioRepository.buscarPorNombreUsuario(nombre);

        // Verificar si el usuario está registrado
        if(usuario == null) {
            context.redirect("/login?error=unregistered");
            return;
        }
        if(usuario.getEsValido()) {
            if (Encriptador.verificarContrasenia(contrasenia, usuario.getContrasenia())) {
                context.sessionAttribute("usuario_id", usuario.getId());
                context.redirect("/app/portal");
            } else {
                context.redirect("/login?error=invalid_password");
            }
        } else {
            context.render("errorHandlers/usuario_no_activo.hbs");
        }
    }

    // INICIO
    @Override
    public void show(Context context) {
        Usuario usuario = (Usuario) this.usuarioRepository.findById(context.sessionAttribute("usuario_id"));
        Map<String, Object> model = new HashMap<>();
        model.put("usuario", usuario);
        context.render("portal/portal.hbs", model);
    }

    //CERRAR SESION
    public void logout(Context context) {
        context.consumeSessionAttribute("usuario_id");
        context.redirect("login");
    }

    //SIGNUP
    @Override
    public void create(Context context) {
        Map<String, Object> model = new HashMap<>();
        String errorMessage = context.sessionAttribute("errorMessage");
        if (errorMessage != null) {
            model.put("errorMessage", errorMessage);
        }
        context.render("signup.hbs", model);
    }

    @Override
    public void save(Context context) {
        String nombre = context.formParam("nombre");
        String apellido = context.formParam("apellido");
        String username = context.formParam("usuario");
        String contrasenia = context.formParam("contraseña");
        String telefono = context.formParam("telefono");
        String mail = context.formParam("mail");


        String contraseniaEncriptada;
        boolean contraValida = false;
        try {
            contraValida = validadorDeContrasenia.validarContrasenia(username, contrasenia);
        } catch (RuntimeException e) {
            e.printStackTrace();
            context.sessionAttribute("errorMessage", e.getMessage());
            context.redirect("/signup");
            return;
        }
        try {
            if(contraValida) {
                contraseniaEncriptada = Encriptador.encriptarContrasenia(contrasenia);

                Usuario usuario = new Usuario(username, contraseniaEncriptada);
                usuarioRepository.save(usuario);

                ConfigNotificacion config = (ConfigNotificacion) configNotificacionRepository.findById(1L);
                Persona persona = new Persona(usuario, nombre, apellido, mail, telefono, config);
                personaRepository.save(persona);

                Map<String, Object> model = new HashMap<>();
                model.put("mensaje", "Se registro correctamente :)");
                context.render("login.hbs", model);
            } else
                context.status(500);
        } catch (NoSuchAlgorithmException e)  {
            e.printStackTrace();
            context.status(500);
        }
    }

    @Override
    public void edit(Context context) {}

    @Override
    public void update(Context context) {}

    @Override
    public void delete(Context context) {}
}

