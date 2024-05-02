package models.entities.domain.entidades;

import models.entities.domain.Persistente;
import models.entities.domain.georef.Municipio;
import models.entities.domain.georef.Provincia;
import models.entities.domain.georef.Localidad;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "entidad")
@Setter
@Getter
@NoArgsConstructor
public class Entidad extends Persistente {

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "entidad", cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    private List<Establecimiento> establecimientos;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne
    @JoinColumn(name = "tipoEntidad_id", referencedColumnName = "id")
    private TipoEntidad tipo;

    @ManyToOne
    @JoinColumn(name = "localidad_id", referencedColumnName = "id")
    private Localidad localidad;

    @ManyToOne
    @JoinColumn(name = "municipio_id", referencedColumnName = "id")
    private Municipio municipio;

    @ManyToOne
    @JoinColumn(name = "provincia_id", referencedColumnName = "id")
    private Provincia provincia;

    //Constructor
    public Entidad(String nombre, TipoEntidad tipo, Localidad localidad, Municipio municipio, Provincia provincia) { // 2 de las 3 ubicaciones pueden quedar en null
        this.nombre = nombre;
        this.activo = true;
        this.establecimientos = new ArrayList<>();
        this.tipo = tipo;
        this.provincia = provincia;
        this.municipio = municipio;
        this.localidad = localidad;
    }

    public void agregarEstablecimientos(Establecimiento... establecimientos) {
        Collections.addAll(this.establecimientos, establecimientos);
    }

    public void cambiarEstado() {
        this.activo = !this.activo;
    }

    public Establecimiento getEstablecimientoById(Long id) {
        return establecimientos.stream().filter(e -> Objects.equals(e.getId(), id)).toList().get(0);
    }

    public Long getIdTipoEntidad() {
        return tipo.getId();
    }

    public String getNombreTipoEntidad() {
        return this.tipo.getNombre();
    }
}
