package models.entities.domain.notificaciones.estrategia.Email;

import models.entities.domain.notificaciones.Notificacion;

public interface AdapterEmail {
    void notificar(Notificacion notificacion);
}
