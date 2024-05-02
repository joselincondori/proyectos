package com.stack.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "entidad")
@Getter
@NoArgsConstructor
public class Entidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "entidad", cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    private List<Establecimiento> establecimientos;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne
    @JoinColumn(name = "tipoEntidad_id", referencedColumnName = "id")
    private TipoEntidad tipo;

    public Long getIdTipoEntidad() {
        return tipo.getId();
    }

    public String getNombreTipoEntidad() {
        return this.tipo.getNombre();
    }
}
