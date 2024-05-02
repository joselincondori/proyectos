package models.entities.domain.notificaciones.tiempo;

import models.entities.converters.LocalTimeAtributeConverter;
import models.entities.domain.notificaciones.InfoNotificacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("sin_apuro")
public class SinApuro extends TiempoNotificacion {
    @OneToOne
    @JoinColumn(name = "notificacion_programada_id", referencedColumnName = "id")
    private NotificacionProgramada notificacionProgramada;

    @ElementCollection
    @Column(name = "horario_envio")
    @Convert(converter = LocalTimeAtributeConverter.class)
    private List<LocalTime> horarios;


    public SinApuro() {
        this.notificacionProgramada = new NotificacionProgramada();
        this.horarios = new ArrayList<>();
    }

    public void agregarHorariosEnvio(LocalTime horario) {
        this.horarios.add(horario);
    }

    @Override
    public Boolean chequearTiempo(InfoNotificacion infoNotificacion) {
        notificacionProgramada.recibirNotificacion(infoNotificacion, horarioDeEnvioProximo());
        return false;
    }

    private LocalTime horarioDeEnvioProximo() {
        LocalTime proximo = null;
        if(horarios.size() == 1) {
            proximo = horarios.get(0);
        } else {
            LocalTime now = LocalTime.now();
            int diferenciaHora = 24;
            for(LocalTime horario : horarios) {
                if(horario.isAfter(now) && horario.getHour() - now.getHour() < diferenciaHora) {
                        diferenciaHora = horario.getHour() - now.getHour();
                        proximo = horario;
                }
            }
        }
        return proximo;
    }
}
