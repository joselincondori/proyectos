package controllers.dtos.servicio2.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IncidenteDto {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime fechaApertura;
    private PersonaDto usuarioReportador;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime fechaCierre;
    private PersonaDto usuarioAnalizador;
    private ServicioDto servicioAfectado;
}
