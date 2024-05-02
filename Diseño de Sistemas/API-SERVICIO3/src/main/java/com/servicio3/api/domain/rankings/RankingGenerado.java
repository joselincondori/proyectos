package com.servicio3.api.domain.rankings;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RankingGenerado {
    private String descripcion;
    private List<EntradaRanking> ranking;

    public RankingGenerado(String descripcion) {
        this.descripcion = descripcion;
        this.ranking = new ArrayList<>();
    }
}
