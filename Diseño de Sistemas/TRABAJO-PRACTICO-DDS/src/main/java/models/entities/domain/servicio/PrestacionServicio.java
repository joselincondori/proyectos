package models.entities.domain.servicio;

import models.entities.domain.Persistente;
import models.entities.domain.entidades.Establecimiento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "prestacion_servicio")
@Getter
@Setter
@NoArgsConstructor
public class PrestacionServicio extends Persistente {
    @Column(name = "descripcion")
    private String descripcion;
    @ManyToOne
    @JoinColumn(name = "establecimiento_id", referencedColumnName = "id")
    private Establecimiento establecimiento;
    @ManyToOne
    @JoinColumn(name = "servicio_id", referencedColumnName = "id")
    private Servicio servicio;

    @Column(name = "activo") // para borrado logico
    private Boolean activo;

    public PrestacionServicio(String descripcion, Establecimiento establecimiento, Servicio servicio) {
        this.descripcion = descripcion;
        this.establecimiento = establecimiento;
        this.servicio = servicio;
        this.activo = true;
    }

    public String getNombreServicio() {
        return servicio.getNombre();
    }

    public String getNombreEstablecimiento() {
        return establecimiento.getNombre();
    }
}
