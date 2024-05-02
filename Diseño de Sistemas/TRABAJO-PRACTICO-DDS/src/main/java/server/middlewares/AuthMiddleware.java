package server.middlewares;

import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import models.entities.domain.roles.Rol;
import models.entities.sesion.Usuario;
import models.repositories.RolRepository;
import models.repositories.UsuarioRepository;
import server.utils.TipoRol;
import server.exceptions.AccessDeniedException;

import java.util.Objects;

public class AuthMiddleware {

    private static final UsuarioRepository usuarioRepository = new UsuarioRepository();
    private static final RolRepository rolRepository = new RolRepository();

    public static void apply(JavalinConfig config) {
        config.accessManager(((handler, context, routeRoles) -> {
            TipoRol userRole = getUserRoleType(context);

            if(routeRoles.size() == 0 || routeRoles.contains(userRole)) {
                handler.handle(context);
            }
            else {
                throw new AccessDeniedException();
            }
        }));
    }

    private static TipoRol getUserRoleType(Context context) {
        Long usuarioId = context.sessionAttribute("usuario_id");
        if(usuarioId != null) {
            Usuario usuario = (Usuario) usuarioRepository.findById(usuarioId);
            Rol rolAdminPlataforma = rolRepository.getRolAdminPlataforma();
            if (usuario.getRol() != null && Objects.equals(usuario.getRol().getDescripcion(), rolAdminPlataforma.getDescripcion()))
                return TipoRol.ADMINISTRADOR_PLATAFORMA;
        }
        return null;
    }
}
