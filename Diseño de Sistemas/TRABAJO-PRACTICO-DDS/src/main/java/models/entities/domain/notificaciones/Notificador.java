package models.entities.domain.notificaciones;

import models.entities.domain.persona.Persona;

import java.util.List;

public class Notificador {
    private static Notificador instance;

    public static Notificador getInstance() {
        if(instance == null) {
            instance = new Notificador();
        }
        return instance;
    }

    private Notificador() {}

    public void notificar(DatosNotificacion datos, List<Persona> destinatarios) {
        for(Persona destinatario : destinatarios) {
            InfoNotificacion infoNotificacion = new InfoNotificacion(datos, destinatario);
            destinatario.notificar(infoNotificacion);
        }
    }
}
