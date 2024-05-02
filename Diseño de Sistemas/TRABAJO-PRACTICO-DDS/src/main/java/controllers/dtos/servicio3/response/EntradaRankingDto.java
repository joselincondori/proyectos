package controllers.dtos.servicio3.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EntradaRankingDto {
    private int puesto;
    private Float puntaje;
    private String entidad;
    private Long entidadId;
}
