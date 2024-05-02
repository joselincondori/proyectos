package models.entities.domain.notificaciones.estrategia.Email;

import config.Config;
import models.entities.domain.notificaciones.Notificacion;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class JavaxMail implements AdapterEmail {
    private static final String remitente = Config.obtenerInstancia().obtenerDelConfig("remitente");
    private static final String contrasenia = Config.obtenerInstancia().obtenerDelConfig("contrasenia");

    public void notificar(Notificacion notificacion) {
        String destinatario = notificacion.getDestinatario().getMail();
        String cuerpo = notificacion.getCuerpo();
        String asunto = notificacion.getAsunto();

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Host de Gmail = smtp.gmail.com
        props.put("mail.smtp.port", "587"); // Puerto Gmail = 587
        props.put("mail.smtp.auth", "true"); // Se requiere autorizacion? = Verdadero
        props.put("mail.smtp.starttls.enable", "true"); // Utiliza encriptación TLS? = Verdadero

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, contrasenia);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(asunto);
            message.setText(cuerpo);
            System.out.println("Enviando...");
            Transport.send(message);
            System.out.println("Enviado correctamente");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
