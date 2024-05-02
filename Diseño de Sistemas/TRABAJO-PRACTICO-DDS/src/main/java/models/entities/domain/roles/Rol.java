package models.entities.domain.roles;

import lombok.Getter;
import lombok.Setter;
import models.entities.domain.Persistente;
import lombok.NoArgsConstructor;
import server.utils.TipoRol;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "rol")
@NoArgsConstructor
@Getter
@Setter
public class Rol extends Persistente {

    @Column(name = "descripcion")
    private String descripcion;

    public Rol(String descripcion) {
        this.descripcion = descripcion;
    }
}
