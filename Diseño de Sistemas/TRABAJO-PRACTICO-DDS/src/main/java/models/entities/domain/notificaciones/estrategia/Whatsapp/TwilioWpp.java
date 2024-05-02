package models.entities.domain.notificaciones.estrategia.Whatsapp;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import config.Config;
import models.entities.domain.notificaciones.Notificacion;


public class TwilioWpp implements AdapterWpp {
    String ACCOUNT_SID = Config.obtenerInstancia().obtenerDelConfig("account_sid");
    String AUTH_TOKEN = Config.obtenerInstancia().obtenerDelConfig("auth_token");
    private static TwilioWpp instancia = null;
    public void notificar(Notificacion notificacion) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(notificacion.getDestinatario().getTelefono()),
                        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                        notificacion.getAsunto() + "\n" + notificacion.getCuerpo())
                .create();

        System.out.println("Mensaje enviado correctamente. ID del mensaje: " + message.getSid());
    }

    public static TwilioWpp getInstance() {
        if(instancia == null) {
            instancia = new TwilioWpp();
        }
        return instancia;
    }
}
