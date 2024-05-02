package models.entities.domain.rankings;

import controllers.dtos.servicio3.response.EntradaRankingDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RankingGenerado {
    private String descripcion;
    private LocalDate fechaCreacion;
    private List<EntradaRanking> ranking;

    public RankingGenerado(String descrip){
        this.descripcion = descrip;
        this.fechaCreacion = LocalDate.now();
        this.ranking = new ArrayList<>();
    }

    public void agregarEntrada(EntradaRanking entrada) {
        ranking.add(entrada);
    }
}
