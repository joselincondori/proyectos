package models.entities.domain.servicio;

import models.entities.domain.Persistente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.entities.domain.entidades.OrganismoDeControl;

import javax.persistence.*;

@Entity
@Table(name = "servicio")
@Getter
@Setter
@NoArgsConstructor
public class Servicio extends Persistente {
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne
    @JoinColumn(name = "organismo_control_id", referencedColumnName = "id")
    private OrganismoDeControl organismo;

    public Servicio(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = true;
    }
}