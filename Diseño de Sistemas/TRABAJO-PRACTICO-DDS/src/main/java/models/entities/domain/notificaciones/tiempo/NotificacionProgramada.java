package models.entities.domain.notificaciones.tiempo;

import models.entities.converters.LocalTimeAtributeConverter;
import models.entities.domain.Persistente;
import models.entities.domain.comunidad.Comunidad;
import models.entities.domain.notificaciones.InfoNotificacion;
import models.entities.domain.notificaciones.Notificacion;
import models.entities.domain.notificaciones.estrategia.EstrategiaNotificacion;
import models.entities.domain.persona.Persona;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "notificacion_programada")
public class NotificacionProgramada extends Persistente {

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "notificacion_programada_id")
    private List<InfoNotificacion> pendientes;

    @Column(name = "horario_envio")
    @Convert(converter = LocalTimeAtributeConverter.class)
    private LocalTime horarioEnvio;

    public NotificacionProgramada() {
        this.pendientes = new ArrayList<>();
    }

    public void recibirNotificacion(InfoNotificacion infoNotificacion, LocalTime proxHorario) {
        pendientes.add(infoNotificacion);
        if(esHoraDeMandar()) {
            notificar();
        }
        if(horarioEnvio == null) {
            this.horarioEnvio = proxHorario;
        }
    }

    public void notificar() { // este metodo lo ejecuta el cron job (NotificacionProgramadaJob)
        if(esHoraDeMandar()) {
            eliminarIncidentesCerrados();
            getEstrategiaNotificacion().notificar(this.crearEnviable());
            resetearNotificacionProgramada();
        }
    }

    private EstrategiaNotificacion getEstrategiaNotificacion() {
        return getUsuario().getConfigNotificacion().getEstrategiaNotificacion();
    }

    private Persona getUsuario() {
        return pendientes.get(0).getDestinatario();
    }

    private Comunidad getComunidad() {
        return pendientes.get(0).getComunidad();
    }

    private Boolean esHoraDeMandar() {
        if(horarioEnvio == null) {
            return false;
        } else {
            LocalTime now = LocalTime.now();
            return now.minus(10, ChronoUnit.MINUTES).isBefore(horarioEnvio);
        }
    }

    private void resetearNotificacionProgramada() {
        pendientes = new ArrayList<>();
        horarioEnvio = null;
    }
    public Notificacion crearEnviable() {
        StringBuilder cuerpo = new StringBuilder(new String());
        for(InfoNotificacion noti : pendientes)
            cuerpo.append(noti.getTitulo()).append(": ").append("\n").append(noti.getMensaje()).append("\n").append("\n");
        System.out.println(cuerpo.toString());
        return new Notificacion("Nuevas notificaciones de " + getComunidad().getNombre(), cuerpo.toString(), getUsuario());
    }

    private void eliminarIncidentesCerrados() {
        pendientes.removeIf(n -> !n.getIncidente().estaAbierto());
    }
}
