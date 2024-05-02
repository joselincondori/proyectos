package models.entities.domain.rankings;

import models.entities.domain.entidades.Entidad;
import models.entities.domain.incidentes.Incidente;

import java.util.List;

public interface ConceptoRanking {
    RankingGenerado generar(List<Entidad> entidades, List<Incidente> incidentes);
}
