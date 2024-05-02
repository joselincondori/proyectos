package models.entities.domain.notificaciones.estrategia.Whatsapp;
import models.entities.domain.notificaciones.Notificacion;

public interface AdapterWpp {
    void notificar (Notificacion notificacion);
}
