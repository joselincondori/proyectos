package models.entities.domain.notificaciones.estrategia;

import models.entities.domain.notificaciones.Notificacion;

public interface EstrategiaNotificacion {
    public void notificar(Notificacion notificacion);
}
