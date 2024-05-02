package controllers.dtos.servicio2.response;

import controllers.dtos.servicio2.request.ComunidadDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConfianzaComunidadResponseDto {
    private ComunidadDto comunidad;
    private float nuevoPuntaje;
    private int gradoDeConfianzaActual;
}
