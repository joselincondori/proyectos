package models.entities.domain.entidades;

import models.entities.domain.Persistente;
import models.entities.domain.persona.Persona;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Entity
@Table(name = "entidad_prestadora")
@Getter
@Setter
@NoArgsConstructor
public class EntidadPrestadora extends Persistente {
    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "representante_id", referencedColumnName = "id")
    private Persona representante;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "entidad_prestadora_id")
    private List<Entidad> entidadesQueControla;

    public EntidadPrestadora(String nombre, Persona representante) {
        this.nombre = nombre;
        this.representante = representante;
        this.entidadesQueControla = new ArrayList<>();
    }

    public void agregarEntidadesQueControlan(Entidad ... entidadesQueControla){
        Collections.addAll(this.entidadesQueControla, entidadesQueControla);
    }
}
