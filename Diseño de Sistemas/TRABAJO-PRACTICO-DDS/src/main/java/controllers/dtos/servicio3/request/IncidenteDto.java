package controllers.dtos.servicio3.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class IncidenteDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime fechaHoraApertura;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime fechaHoraCierre;
    private Long idEstablecimiento;
}
