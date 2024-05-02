package controllers.dtos.servicio2.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ConfianzaComunidadRequestDto {
    private ComunidadDto comunidad;
    private List<IncidenteDto> incidentes;
}
