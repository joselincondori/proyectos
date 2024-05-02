package models.entities.domain.entidades;

import models.entities.domain.Persistente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_entidad")
@Getter
@Setter
@NoArgsConstructor

public class TipoEntidad extends Persistente {
    @Column(name = "nombre")
    private String nombre;
    public TipoEntidad(String nombre) {
        this.nombre = nombre;
    }
}
