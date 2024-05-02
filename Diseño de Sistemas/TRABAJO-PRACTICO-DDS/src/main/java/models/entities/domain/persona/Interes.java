package models.entities.domain.persona;

import models.entities.domain.entidades.Entidad;
import models.entities.domain.georef.Municipio;
import models.entities.domain.georef.Provincia;
import models.entities.domain.servicio.Servicio;
import models.entities.domain.georef.Localidad;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Getter
@Setter
@Embeddable
public class Interes {

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "entidad_interes",
            joinColumns = { @JoinColumn(name = "persona_interesada_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "entidad_id", referencedColumnName = "id") }
    )
    private List<Entidad> entidades;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "servicio_interes",
            joinColumns = { @JoinColumn(name = "persona_interesada_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "servicio_id", referencedColumnName = "id") }
    )
    private List<Servicio> servicios;

    @ManyToOne
    @JoinColumn(name = "localidad_interes_id", referencedColumnName = "id")
    private Localidad localidad;

    @ManyToOne
    @JoinColumn(name = "municipio_interes_id", referencedColumnName = "id")
    private Municipio municipio;

    @ManyToOne
    @JoinColumn(name = "provincia_interes_id", referencedColumnName = "id")
    private Provincia provincia;

    public Interes() {
        this.entidades = new ArrayList<>();
        this.servicios = new ArrayList<>();
    }

    public void agregarInteres(Entidad ... entidades){
        Collections.addAll(this.entidades, entidades);
    }
    public void agregarInteres(Servicio ... servicios){
        Collections.addAll(this.servicios, servicios);
    }
}
