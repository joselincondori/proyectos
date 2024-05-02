package models.entities.domain.georef;
import models.entities.domain.Persistente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "municipio")
@Getter
@Setter
@NoArgsConstructor
public class Municipio extends Persistente{
    @Column(name = "nombre")
    private String nombre;
    @ManyToOne
    @JoinColumn(name = "provincia")
    private Provincia provincia;

    public Municipio(String nombre, Provincia provincia) {
        this.nombre = nombre;
        this.provincia = provincia;
    }
}
