package controllers.dtos.servicio3.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RankingGeneradoDto {
    private String descripcion;
    private List<EntradaRankingDto> ranking;
}
