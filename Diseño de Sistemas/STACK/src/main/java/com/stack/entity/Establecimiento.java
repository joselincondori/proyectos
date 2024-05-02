package com.stack.entity;

import lombok.Getter;

import jakarta.persistence.*;

@Entity
@Table(name = "establecimiento")
@Getter
public class Establecimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "entidad_id", referencedColumnName = "id")
    private Entidad entidad;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne
    @JoinColumn(name = "tipo_establecimiento_id", referencedColumnName = "id")
    private TipoEstablecimiento tipo;

    public Long getIdTipoEstablecimiento() {
        return tipo.getId();
    }

    public String getNombreTipoEstablecimiento() {
        return this.tipo.getNombre();
    }
}