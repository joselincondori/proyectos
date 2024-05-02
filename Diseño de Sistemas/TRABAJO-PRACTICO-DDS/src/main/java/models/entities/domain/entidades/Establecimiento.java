package models.entities.domain.entidades;

import models.entities.domain.Persistente;
import models.entities.domain.georef.Ubicacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "establecimiento")
@Getter
@Setter
public class Establecimiento extends Persistente {
    @ManyToOne
    @JoinColumn(name = "entidad_id", referencedColumnName = "id")
    private Entidad entidad;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @Embedded
    private Ubicacion ubicacion;

    @ManyToOne
    @JoinColumn(name = "tipo_establecimiento_id", referencedColumnName = "id")
    private TipoEstablecimiento tipo;

    public Establecimiento() {    }
    public Establecimiento(String nombre, Ubicacion ubicacion, TipoEstablecimiento tipo, Entidad entidad) {
        this.nombre = nombre;
        this.activo = true;
        this.ubicacion = ubicacion;
        this.tipo = tipo;
        this.entidad = entidad;
    }

    public void cambiarEstado() {
        this.activo = !this.activo;
    }

    public Long getIdTipoEstablecimiento() {
        return tipo.getId();
    }

    public String getNombreTipoEstablecimiento() {
        return this.tipo.getNombre();
    }
}
