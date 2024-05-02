package models.entities.domain.incidentes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.entities.converters.LocalDateTimeAtributeConverter;
import models.entities.domain.Persistente;
import models.entities.domain.persona.Persona;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "sugerencia_revision")
@NoArgsConstructor
public class SugerenciaRevision extends Persistente {
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "incidente_id", referencedColumnName = "id")
    private Incidente incidente;
    @ManyToOne
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    private Persona analizador;
    @Column(name = "fecha_hora_envio")
    @Convert(converter = LocalDateTimeAtributeConverter.class)
    private LocalDateTime fechaHoraEnvio;
    @Column(name = "fecha_hora_resolucion")
    @Convert(converter = LocalDateTimeAtributeConverter.class)
    private LocalDateTime fechaHoraResolucion;

    public SugerenciaRevision(Incidente incidente, Persona analizador) {
        this.incidente = incidente;
        this.analizador = analizador;
        this.fechaHoraEnvio = LocalDateTime.now();
    }

    public Boolean estaResuelta() {
        return this.fechaHoraResolucion != null;
    }

    public Boolean noResueltaHaceMasDe(int horas) {
        Duration duration = Duration.between(fechaHoraEnvio, LocalDateTime.now());
        return duration.toHours() >= horas;
    }

    public void cerrarSinResolverIncidente() {
        this.fechaHoraResolucion = LocalDateTime.now();
    }

    public void cerrarYResolverIncidente(Persona analizador) {
        this.fechaHoraResolucion = LocalDateTime.now();
        this.incidente.cerrar(analizador);
    }

    public Boolean incidenteEstaAbierto() {
        return this.incidente.estaAbierto();
    }

    public String getNombreEstablecimiento() {
        return incidente.getPrestacionServicio().getNombreEstablecimiento();
    }

    public String getNombreEntidad() {
        return incidente.getPrestacionServicio().getEstablecimiento().getEntidad().getNombre();
    }

    public String getNombreServicio() {
        return incidente.getPrestacionServicio().getNombreServicio();
    }
}
