package controllers.dtos.servicio2.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ConfianzaPersonaRequestDto {
    private PersonaDto usuario;
    private List<IncidenteDto> incidentes;
}
