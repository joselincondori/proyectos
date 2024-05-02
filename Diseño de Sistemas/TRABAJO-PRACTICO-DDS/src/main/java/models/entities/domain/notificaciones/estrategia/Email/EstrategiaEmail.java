package models.entities.domain.notificaciones.estrategia.Email;

import models.entities.domain.notificaciones.Notificacion;
import models.entities.domain.notificaciones.estrategia.EstrategiaNotificacion;
import lombok.Getter;
import lombok.Setter;

public class EstrategiaEmail implements EstrategiaNotificacion {
    private AdapterEmail adapterEmail;

    public EstrategiaEmail() {
        this.adapterEmail = new JavaxMail();
    }

    @Override
    public void notificar(Notificacion notificacion) {
        adapterEmail.notificar(notificacion);
    }
}
