package models.entities.domain.notificaciones.tiempo;

import models.entities.domain.Persistente;
import models.entities.domain.notificaciones.InfoNotificacion;

import javax.persistence.*;

@Entity
@Table(name = "tiempo_notificacion")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class TiempoNotificacion extends Persistente {
    public abstract Boolean chequearTiempo(InfoNotificacion infoNotificacion);
}
