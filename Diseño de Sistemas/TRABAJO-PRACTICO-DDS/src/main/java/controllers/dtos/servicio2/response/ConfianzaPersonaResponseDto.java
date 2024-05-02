package controllers.dtos.servicio2.response;

import controllers.dtos.servicio2.request.PersonaDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConfianzaPersonaResponseDto {
    private PersonaDto usuario;
    private float nuevoPuntaje;
    private int gradoDeConfianzaActual;
}
