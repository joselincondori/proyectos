package models.entities.domain.entidades;

import models.entities.domain.Persistente;
import models.entities.domain.servicio.Servicio;
import models.entities.domain.persona.Persona;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Entity
@Table(name = "organismo_control")
@Getter
@Setter
@NoArgsConstructor
public class OrganismoDeControl extends Persistente {
    @Column(name = "nombre")
    private String nombre;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Persona representante;

    @OneToMany(mappedBy = "organismo", cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    private List<Servicio> serviciosQueControla;

    public OrganismoDeControl(String nombre, Persona representante) {
        this.nombre = nombre;
        this.representante = representante;
        this.serviciosQueControla = new ArrayList<>();
    }

    public void agregarServiciosQueControla(Servicio ... servicioss) {
        Collections.addAll(this.serviciosQueControla, servicioss);
    }
}
