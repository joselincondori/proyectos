package models.entities.converters;

import models.entities.domain.roles.Rol;
import server.utils.TipoRol;

public class RolTipoRolConverter {
    public TipoRol convertRol(Rol rol) {
        String descripcion = rol.getDescripcion();
        TipoRol tipoRol = null;
        switch (descripcion) {
            case "admin_plataforma": tipoRol = TipoRol.ADMINISTRADOR_PLATAFORMA; break;
            case "admin_comunidad": tipoRol = TipoRol.ADMINISTRADOR_COMUNIDAD; break;
        }
        return tipoRol;
    }
}
