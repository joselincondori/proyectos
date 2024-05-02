package notificadores.estrategiasEnvio;

import static org.mockito.Mockito.mock;

public class notificacionesMailTest {/*
    @Test
    public void usuarioConEstrategiaMailNotificaConAdapter() {
        AdapterEmail notificadorMail = mock(JavaxMail.class);

        Usuario usuario = new Usuario("eduardo", "lksafklajsfklas", "hola", "chau", "ejemplo@gmail.com", null);
        Comunidad comunidad = new Comunidad("comunidad1");
        TiempoNotificacion tiempoNotificacion = new Inmediata();
        EstrategiaNotificacion estrategiaNotificacion = notificadorMail;
        ConfigNotificacion config = new ConfigNotificacion(tiempoNotificacion,estrategiaNotificacion);
        usuario.setConfigNotificacion(config);

        Notificacion notificacion = new Notificacion(usuario,"Banio roto","inundado",comunidad);

        usuario.notificar(notificacion);

        Mockito.doNothing().when(notificadorMail).notificar(notificacion);

        Mockito.verify(notificadorMail).notificar(notificacion);
    }

    @Test
    public void seEnviaRealmenteUnMail() {
        Usuario usuario = new Usuario("Roberto", "orduela24", "Eldis", "Capacitado", "jocondori@frba.utn.edu.ar", null);
        Comunidad comunidad = new Comunidad("comunidad1");
        Notificacion notificacion = new Notificacion(usuario,"Banio roto","inundado",comunidad);
        AdapterEmail notificadorMail = new JavaxMail();
        notificadorMail.notificar(notificacion);
    }*/
}
