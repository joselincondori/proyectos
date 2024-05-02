package models.entities.domain.notificaciones;

import models.entities.domain.Persistente;
import models.entities.domain.comunidad.Comunidad;
import models.entities.domain.incidentes.Incidente;
import models.entities.domain.notificaciones.tiempo.TiempoNotificacion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatosNotificacion extends Persistente {
    private Comunidad comunidad;
    private Incidente incidente;
    private String mensaje;
    private String titulo;
    private TiempoNotificacion tiempoNotificacion;

    public DatosNotificacion(Comunidad comunidad, Incidente incidente, String mensaje, String titulo, TiempoNotificacion tiempoNotificacion) {
        this.comunidad = comunidad;
        this.incidente = incidente;
        this.mensaje = mensaje;
        this.titulo = titulo;
        this.tiempoNotificacion = tiempoNotificacion;
    }
}
