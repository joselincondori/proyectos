package models.entities.domain.incidentes;

import models.entities.converters.LocalDateTimeAtributeConverter;
import models.entities.domain.Persistente;
import models.entities.domain.comunidad.Comunidad;
import models.entities.domain.persona.Persona;
import models.entities.domain.servicio.PrestacionServicio;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "incidente")
@Getter
@Setter
@NoArgsConstructor
public class Incidente extends Persistente {
    @ManyToOne
    @JoinColumn(name = "comunidad_id", referencedColumnName = "id")
    private Comunidad comunidad;

    @Column(name = "fecha_hora_apertura")
    @Convert(converter = LocalDateTimeAtributeConverter.class)
    private LocalDateTime fechaHoraApertura;

    @Column(name = "fecha_hora_cierre")
    @Convert(converter = LocalDateTimeAtributeConverter.class)
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

    public Incidente(Comunidad comunidad, String observaciones, PrestacionServicio prestacionServicio, Persona creador) {
        this.comunidad = comunidad;
        this.observaciones = observaciones;
        this.prestacionServicio = prestacionServicio;
        this.fechaHoraApertura = LocalDateTime.now();
        this.fechaHoraCierre = null;
        this.creador = creador;
    }

    public Duration calcularDuracion() {
        if(fechaHoraCierre == null) {
            return null;
        } else {
            return Duration.between(fechaHoraApertura, fechaHoraCierre);
        }
    }

    public int calcularImpactoSobreComunidad() {
        return comunidad.calcularNumeroAfectados();
    }

    public void cerrar(Persona analizador) {
        this.fechaHoraCierre = LocalDateTime.now();
        this.analizador = analizador;
        //comunidad.notificarCierreIncidente(this); TODO cambiar esto, tiene que ir en el controller
    }

    public Boolean estaAbierto() {
        return fechaHoraCierre == null;
    }

    public String getNombreComunidad() {
        return comunidad.getNombre();
    }

    public String getNombreCreador() {
        return creador.getNombre();
    }

    public String getNombreAnalizador() {
        return analizador != null? analizador.getNombre() : null;
    }

    public String getNombreServicio() {
        return prestacionServicio.getNombreServicio();
    }

    public String getNombreEstablecimiento() {
        return prestacionServicio.getNombreEstablecimiento();
    }

    public String getNombreEntidad() {
        return prestacionServicio.getEstablecimiento().getEntidad().getNombre();
    }

    public String getNombreTipoEstablecimiento() {return prestacionServicio.getEstablecimiento().getNombreTipoEstablecimiento();}

    public String getEstado() {
        String estado = "Cerrado";
        if(this.estaAbierto())
            estado = "Abierto";
        return estado;
    }

    public Long getIdEstablecimiento() {
        return this.prestacionServicio.getEstablecimiento().getId();
    }
}
