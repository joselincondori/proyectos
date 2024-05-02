package models.entities.domain.georef;

import models.entities.domain.Persistente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "localidad")
@Getter
@Setter
@NoArgsConstructor
public class Localidad extends Persistente {
    @Column(name = "nombre")
    private String nombre;
    @ManyToOne
    @JoinColumn(name = "municipio")
    private Municipio municipio;

    public Localidad(String nombre, Municipio municipio) {
        this.nombre = nombre;
        this.municipio = municipio;
    }
}
