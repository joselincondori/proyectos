package server.utils;

import io.javalin.security.RouteRole;

public enum TipoRol  implements RouteRole {
    ADMINISTRADOR_PLATAFORMA,
    ADMINISTRADOR_COMUNIDAD
}
