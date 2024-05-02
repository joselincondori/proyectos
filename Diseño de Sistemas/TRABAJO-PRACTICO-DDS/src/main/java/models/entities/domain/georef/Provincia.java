package models.entities.domain.georef;
import models.entities.domain.Persistente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "provincia")
@Getter
@Setter
@NoArgsConstructor
public class Provincia extends Persistente{
    @Column(name = "nombre")
    private String nombre;

    public Provincia(String nombre) {
        this.nombre = nombre;
    }
}
