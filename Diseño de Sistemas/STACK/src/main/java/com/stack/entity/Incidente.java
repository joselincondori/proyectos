package com.stack.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "incidente")
@Getter
@Setter
@NoArgsConstructor
public class Incidente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comunidad_id", referencedColumnName = "id")
    private Comunidad comunidad;

    @Column(name = "fecha_hora_apertura")
    private LocalDateTime fechaHoraApertura;

    @Column(name = "fecha_hora_cierre")
    private LocalDateTime fechaHoraCierre;

    @Column(name = "observaciones")
    private String observaciones;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "prestacion_servicio_id", referencedColumnName = "id")
    private PrestacionServicio prestacionServicio;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "creador_id", referencedColumnName = "id")
    private Persona creador;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "analizador_id", referencedColumnName = "id")
    private Persona analizador;

    public Boolean estaAbierto() {
        return fechaHoraCierre == null;
    }

    public String getNombreComunidad() {
        return comunidad.getNombre();
    }

    public String getNombreCreador() {
        return creador.getNombre();
    }

    public String getNombreAnalizador() { return analizador != null? analizador.getNombre() : null; }

    public String getNombreServicio() {
        return prestacionServicio.getNombreServicio();
    }

    public String getNombreEstablecimiento() {
        return prestacionServicio.getNombreEstablecimiento();
    }

    public String getEstado() {
        String estado = "Cerrado";
        if(this.estaAbierto())
            estado = "Abierto";
        return estado;
    }
}

