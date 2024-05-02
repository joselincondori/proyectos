package models.entities.domain.persona;

import models.entities.converters.EstretegiaNotificacionAtributeConverter;
import models.entities.domain.Persistente;
import models.entities.domain.notificaciones.InfoNotificacion;
import models.entities.domain.notificaciones.estrategia.EstrategiaNotificacion;
import models.entities.domain.notificaciones.tiempo.TiempoNotificacion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "config_notificacion")
public class ConfigNotificacion extends Persistente {

    @ManyToOne
    @JoinColumn(name = "tiempo_notificacion_id", referencedColumnName = "id")
    private TiempoNotificacion tiempoNotificacion;

    @Column(name = "estrategia_notificacion")
    @Convert(converter = EstretegiaNotificacionAtributeConverter.class)
    private EstrategiaNotificacion estrategiaNotificacion;

    public ConfigNotificacion(TiempoNotificacion tiempoNotificacion, EstrategiaNotificacion estrategiaNotificacion) {
        this.tiempoNotificacion = tiempoNotificacion;
        this.estrategiaNotificacion = estrategiaNotificacion;
    }

    public void notificar(InfoNotificacion infoNotificacion) {
        if(tiempoNotificacion.chequearTiempo(infoNotificacion)) {
            estrategiaNotificacion.notificar(infoNotificacion.crearEnviable());
        }
    }
}
