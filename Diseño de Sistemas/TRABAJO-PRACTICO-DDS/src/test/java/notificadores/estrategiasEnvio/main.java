package notificadores.estrategiasEnvio;

import static org.mockito.Mockito.mock;

/*
public class main {

    @Test
    public void main(){
        Usuario usuario = new Usuario("eduardo", "lksafklajsfklas", "hola", "chau", "ejemplo@gmail.com", null);
        Comunidad comunidad = new Comunidad("comunidad1");
        TiempoNotificacion tiempoNotificacion = new Inmediata();

        ConfigNotificacion config = new ConfigNotificacion(tiempoNotificacion,null);
        usuario.setConfigNotificacion(config);

        DatosNotificacion datos = new DatosNotificacion(comunidad, null, "hola", "chau", tiempoNotificacion);

        Notificacion notificacion = new Notificacion(datos,usuario);

        configurarEstrategiaMail(usuario, notificacion);

        configurarEstrategiaWpp(usuario, notificacion);
    }

    public void configurarEstrategiaMail(Usuario usuario, Notificacion notificacion) {
        JavaxMail notificador = mock(JavaxMail.class);
        EstrategiaEmail estrategiaEmail = new EstrategiaEmail();

        estrategiaEmail.setAdapterEmail(notificador);

        usuario.getConfigNotificacion().setEstrategiaNotificacion(estrategiaEmail);

        usuario.notificar(notificacion);

        Mockito.doNothing().when(notificador).notificar(notificacion);

        Mockito.verify(notificador).notificar(notificacion);
    }

    public void configurarEstrategiaWpp(Usuario usuario, Notificacion notificacion) {
        TwilioWpp notificador = mock(TwilioWpp.class);
        EstrategiaWpp notificacionWpp = new EstrategiaWpp();

        notificacionWpp.setAdapterWpp(notificador);

        usuario.getConfigNotificacion().setEstrategiaNotificacion(notificacionWpp);

        usuario.notificar(notificacion);

        Mockito.doNothing().when(notificador).notificar(notificacion);

        Mockito.verify(notificador).notificar(notificacion);
    }
}
*/