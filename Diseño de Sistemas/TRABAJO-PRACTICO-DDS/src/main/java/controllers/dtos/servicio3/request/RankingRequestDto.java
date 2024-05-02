package controllers.dtos.servicio3.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RankingRequestDto {
    private List<ComunidadDto> comunidades;
    private List<EntidadDto> entidades;
    private float cnf;
}
