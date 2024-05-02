package models.entities.domain.notificaciones;

import models.entities.converters.LocalDateTimeAtributeConverter;
import models.entities.domain.Persistente;
import models.entities.domain.persona.Persona;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "notificacion")
@NoArgsConstructor
public class Notificacion extends Persistente {
    @Column(name = "asunto")
    private String asunto;
    @Column(name = "cuerpo")
    private String cuerpo;
    @ElementCollection
    @Column(name = "adjuntos")
    private List<String> adjuntos;
    @Column(name = "fecha_hora_envio")
    @Convert(converter = LocalDateTimeAtributeConverter.class)
    private LocalDateTime fechaHoraEnviado;
    @ManyToOne
    @JoinColumn(name = "destinatario_id", referencedColumnName = "id")
    private Persona destinatario;

    public Notificacion(String asunto, String cuerpo, Persona destinatario, String ... adjuntos) {
        this.asunto = asunto;
        this.cuerpo = cuerpo;
        this.adjuntos = new ArrayList<>();
        agregarAdjuntos(adjuntos);
        this.destinatario = destinatario;
    }

    private void agregarAdjuntos(String ... adjuntos) {
        Collections.addAll(this.adjuntos, adjuntos);
    }
}
