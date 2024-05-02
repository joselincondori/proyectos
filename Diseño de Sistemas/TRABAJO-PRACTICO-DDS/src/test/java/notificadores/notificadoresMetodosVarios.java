package notificadores;

import models.entities.domain.comunidad.Comunidad;
import models.entities.domain.incidentes.Incidente;
import models.entities.domain.notificaciones.DatosNotificacion;
import models.entities.domain.notificaciones.InfoNotificacion;
import models.entities.domain.notificaciones.tiempo.NotificacionProgramada;
import models.entities.domain.persona.Persona;
import org.junit.jupiter.api.Test;


public class notificadoresMetodosVarios {

   /* @Test
>>>>>>> b6ae1f32c87647fe5922d5f86223a31055139783
    public void probarParseo() {
        Persona persona = new Persona("eduardo", "lksafklajsfklas", "hola", "chau", "ejemplo@gmail.com", null);
        Comunidad comunidad = new Comunidad("comunidad1");
        Incidente incidente = new Incidente(comunidad, "Se observa una escalera rota", null, null);

        DatosNotificacion datos = new DatosNotificacion(comunidad, incidente, "hola", "chau", null);
        DatosNotificacion datos2 = new DatosNotificacion(comunidad, incidente, "la escalera esta rota", "escalera", null);
        DatosNotificacion datos3 = new DatosNotificacion(comunidad, incidente, "mucha agua en todos lados", "baño pierde", null);
        DatosNotificacion datos4 = new DatosNotificacion(comunidad, incidente, "tablero prendido fuego, alto peligro", "sin luz", null);

        InfoNotificacion infoNotificacion = new InfoNotificacion(datos, persona);
        InfoNotificacion infoNotificacion2 = new InfoNotificacion(datos2, persona);
        InfoNotificacion infoNotificacion3 = new InfoNotificacion(datos3, persona);
        InfoNotificacion infoNotificacion4 = new InfoNotificacion(datos4, persona);

        NotificacionProgramada notificacionProgramada = new NotificacionProgramada();

        notificacionProgramada.recibirNotificacion(infoNotificacion, null);
        notificacionProgramada.recibirNotificacion(infoNotificacion2, null);
        notificacionProgramada.recibirNotificacion(infoNotificacion3, null);
        notificacionProgramada.recibirNotificacion(infoNotificacion4, null);

        notificacionProgramada.crearEnviable();
    }*/

    /*
    @Test
    public void probarEliminacionDeCerrados() {
        Usuario usuario = new Usuario("eduardo", "lksafklajsfklas", "hola", "chau", "ejemplo@gmail.com", null);
        Comunidad comunidad = new Comunidad("comunidad1");
        Incidente incidente = new Incidente(comunidad, "Se observa una escalera rota", null, null);
        Incidente incidenteCerrado = new Incidente(comunidad, "Se observa una escalera rota", null, null);
        incidenteCerrado.setFechaHoraCierre(LocalDateTime.now());

        DatosNotificacion datos = new DatosNotificacion(comunidad, incidenteCerrado, "funciono mal UwU", "ERROR", null);
        DatosNotificacion datos2 = new DatosNotificacion(comunidad, incidente, "la escalera esta rota", "escalera", null);
        DatosNotificacion datos3 = new DatosNotificacion(comunidad, incidente, "mucha agua en todos lados", "baño pierde", null);
        DatosNotificacion datos4 = new DatosNotificacion(comunidad, incidente, "tablero prendido fuego, alto peligro", "sin luz", null);

        Notificacion notificacion = new Notificacion(datos, usuario);
        Notificacion notificacion2 = new Notificacion(datos2, usuario);
        Notificacion notificacion3 = new Notificacion(datos3, usuario);
        Notificacion notificacion4 = new Notificacion(datos4, usuario);

        NotificacionProgramada notificacionProgramada = new NotificacionProgramada();

        notificacionProgramada.recibirNotificacion(notificacion, null);
        notificacionProgramada.recibirNotificacion(notificacion2, null);
        notificacionProgramada.recibirNotificacion(notificacion3, null);
        notificacionProgramada.recibirNotificacion(notificacion4, null);

        //CAMBIAR METODO EN NOTIFICACION PROGRAMADA PARA QUE SEA PUBLICO ANTES DE EJECUTAR
        notificacionProgramada.eliminarIncidentesCerrados();
        notificacionProgramada.crearEnviable();
    }*/


}
