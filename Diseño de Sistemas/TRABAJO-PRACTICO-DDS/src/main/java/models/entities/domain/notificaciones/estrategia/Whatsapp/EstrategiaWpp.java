package models.entities.domain.notificaciones.estrategia.Whatsapp;

import models.entities.domain.notificaciones.Notificacion;
import models.entities.domain.notificaciones.estrategia.EstrategiaNotificacion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstrategiaWpp implements EstrategiaNotificacion {

    private AdapterWpp adapterWpp;

    private static EstrategiaWpp instance;

    public static EstrategiaWpp getInstance() {
        if(instance == null) {
            instance = new EstrategiaWpp();
        }
        return instance;
    }
    @Override
    public void notificar(Notificacion notificacion) {
        adapterWpp.notificar(notificacion);
    }
}
