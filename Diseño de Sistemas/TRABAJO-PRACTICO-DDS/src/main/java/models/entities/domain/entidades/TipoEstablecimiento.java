package models.entities.domain.entidades;

import models.entities.domain.Persistente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_establecimiento")
@Getter
@Setter
@NoArgsConstructor
public class TipoEstablecimiento extends Persistente {
    @Column(name = "nombre")
    private String nombre;

    public TipoEstablecimiento(String nombre) {
        this.nombre = nombre;
    }
}
