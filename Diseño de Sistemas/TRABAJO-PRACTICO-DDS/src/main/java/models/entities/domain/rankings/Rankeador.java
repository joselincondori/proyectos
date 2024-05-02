package models.entities.domain.rankings;

import models.entities.domain.entidades.Entidad;
import models.entities.domain.incidentes.Incidente;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Rankeador {
    private List<Entidad> entidades;
    private List<Incidente> incidentes;
    private List<ConceptoRanking> conceptos;

    private static Rankeador instance;

    public static Rankeador getInstance() {
        if(instance == null) {
            instance = new Rankeador();
        }
        return instance;
    }

    private Rankeador() {}

    public List<RankingGenerado> generarRankings() {
        List<RankingGenerado> rankings = new ArrayList<>();
        for (ConceptoRanking concepto : conceptos) {
            rankings.add(concepto.generar(entidades, incidentes));
        }
        return rankings;
        // TODO llamar a alguien que cree, exporte y mande los rankings
    }
}
