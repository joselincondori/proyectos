package models.entities.domain.comunidad;

import models.entities.domain.Persistente;
import models.entities.domain.notificaciones.InfoNotificacion;
import models.entities.domain.roles.Rol;
import models.entities.domain.persona.Persona;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "miembro")
@Getter
@Setter
@NoArgsConstructor
public class Miembro extends Persistente {
    @ManyToOne()
    @JoinColumn(name = "comunidad_id", referencedColumnName = "id")
    private Comunidad comunidad;

    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    private Persona persona;

    @Column(name = "afectado")
    private Boolean afectado;

    public Miembro(Comunidad comunidad, Persona persona, Boolean afectado) {
        this.comunidad = comunidad;
        this.persona = persona;
        this.afectado = afectado;
    }

    public Boolean correspondeUsuario(Persona persona) {
        return this.persona.equals(persona);
    }

    public void notificar(InfoNotificacion infoNotificacion) {
        persona.notificar(infoNotificacion);
    }

    public String getNombre() {
        return persona.getNombre();
    }

    public String getDescripcionRol() {
        String descripcion = "miembro";
        if(rol != null)
            descripcion = "administrador";
        return descripcion;
    }

    public Boolean esAdmin() {
        if(this.rol == null)
            return false;
        else
            return Objects.equals(rol.getDescripcion(), "admin_comunidad");
    }
}
