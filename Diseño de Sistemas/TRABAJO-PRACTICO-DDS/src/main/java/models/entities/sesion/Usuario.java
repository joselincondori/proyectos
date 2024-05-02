package models.entities.sesion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.entities.domain.Persistente;
import models.entities.domain.roles.Rol;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "usuario")
@NoArgsConstructor
@Setter
@Getter
public class Usuario extends Persistente {
    @Column(name = "username")
    private String username;
    @Column(name = "contrasenia")
    private String contrasenia;
    private Boolean esValido;
    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    private Rol rol;

    public Usuario(String username, String contranenia) {
        this.username = username;
        this.contrasenia = contranenia;
        this.esValido = false;
    }

    public Boolean esAdmin() {
        return rol != null && Objects.equals(rol.getDescripcion(), "admin_plataforma");
    }
}
