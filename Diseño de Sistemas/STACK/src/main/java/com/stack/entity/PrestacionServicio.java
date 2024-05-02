package com.stack.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "prestacion_servicio")
@Getter
@NoArgsConstructor
public class PrestacionServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;
    @ManyToOne
    @JoinColumn(name = "establecimiento_id", referencedColumnName = "id")
    private Establecimiento establecimiento;
    @ManyToOne
    @JoinColumn(name = "servicio_id", referencedColumnName = "id")
    private Servicio servicio;

    @Column(name = "activo")
    private Boolean activo;

    public String getNombreServicio() {
        return servicio.getNombre();
    }

    public String getNombreEstablecimiento() {
        return establecimiento.getNombre();
    }
}
