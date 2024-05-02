package models.entities.domain.notificaciones;

import models.entities.domain.Persistente;
import models.entities.domain.comunidad.Comunidad;
import models.entities.domain.incidentes.Incidente;
import models.entities.domain.notificaciones.tiempo.TiempoNotificacion;
import models.entities.domain.persona.Persona;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "info_notificacion")
@Getter
@Setter
@NoArgsConstructor
public class InfoNotificacion extends Persistente { // TODO hay que hacer un builder para meterle el incidente para que no haya inconsistencias

    @ManyToOne
    @JoinColumn(name = "destinatario_id", referencedColumnName = "id")
    private Persona destinatario;

    @ManyToOne
    @JoinColumn(name = "tiempo_notificacion_id", referencedColumnName = "id")
    private TiempoNotificacion tiempoNotificacion;

    @ManyToOne
    @JoinColumn(name = "incidente_id")
    private Incidente incidente;

    @Column(name = "mensaje")
    private String mensaje;

    @ManyToOne
    @JoinColumn(name = "comunidad_id", referencedColumnName = "id")
    private Comunidad comunidad;

    @Column(name = "titulo")
    private String titulo;

    public InfoNotificacion(DatosNotificacion datos, Persona destinatario) {
        this.destinatario = destinatario;
        this.incidente = datos.getIncidente();
        this.mensaje = datos.getMensaje();
        this.comunidad = datos.getComunidad();
        this.titulo = datos.getTitulo();
        this.tiempoNotificacion = datos.getTiempoNotificacion();
    }

    public Notificacion crearEnviable() {
        return new Notificacion(this.titulo, this.mensaje, this.destinatario);
    }
}

