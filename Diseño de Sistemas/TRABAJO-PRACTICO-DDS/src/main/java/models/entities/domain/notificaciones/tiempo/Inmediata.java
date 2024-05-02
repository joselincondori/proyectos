package models.entities.domain.notificaciones.tiempo;

import models.entities.domain.notificaciones.InfoNotificacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@DiscriminatorValue("inmediata")
public class Inmediata extends TiempoNotificacion {
    @Override
    public Boolean chequearTiempo(InfoNotificacion infoNotificacion) {
        return true;
    }
}
