package models.entities.domain.rankings;

import models.entities.domain.entidades.Entidad;
import models.entities.domain.incidentes.Incidente;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class RankingEntidadesMayorCantIncidentes implements ConceptoRanking {
    private String descripcion;

    public RankingEntidadesMayorCantIncidentes() {
        this.descripcion = "Ranking de entidades por mayor cantidad de incidentes reportados en la semana";
    }

    @Override
    public RankingGenerado generar(List<Entidad> entidades, List<Incidente> incidentes) {
        List<EntradaRanking> entradasRankings = new ArrayList<>();
        List<Incidente> incidentesList = filtrarIncidentesSemanales(incidentes);

        for(Entidad entidad : entidades) {
            List<Incidente> incidentesEntidad = filtrarIncidentesPertenecientesAEntidad(incidentesList, entidad);
            incidentesEntidad = filtrarIncidentesRepetidos(incidentesEntidad);
            EntradaRanking entrada = new EntradaRanking(obtenerPuntaje(incidentesEntidad), entidad);
            entradasRankings.add(entrada);
        }
        // Se ordena la lista de puntajes
        entradasRankings = entradasRankings.stream().sorted(Comparator.comparing(EntradaRanking::getPuntaje).reversed()).toList();

        // agrego la posicion a las entradas
        for(int i=0; i < entradasRankings.size(); i++) {
            entradasRankings.get(i).setPosicion(i+1);
        }

        // Se genera el ranking
        RankingGenerado rankingGenerado = new RankingGenerado(descripcion);
        rankingGenerado.setRanking(entradasRankings);
        return rankingGenerado;
    }

    public Long obtenerPuntaje(List<Incidente> incidentes) {
        Integer size = incidentes.size();
        return size.longValue();
    }

    public List<Incidente> filtrarIncidentesPertenecientesAEntidad(List<Incidente> incidentes, Entidad entidad) {
        return incidentes.stream()
                         .filter(i -> entidad.getEstablecimientos().contains(i.getPrestacionServicio().getEstablecimiento()))
                         .toList();
    }


    // elimina repetidos con mas de 24hs de diferencia
    public List<Incidente> filtrarIncidentesRepetidos(List<Incidente> incidentes) {
        List<Incidente> incidentesList = new ArrayList<>(incidentes);
        for(int i=0; i < incidentesList.size(); i++) {
            for(int j=i+1; j < incidentesList.size(); j++) {
                if(incidentesList.get(i).getPrestacionServicio().equals(incidentesList.get(j).getPrestacionServicio())
                        && incidentesList.get(i).estaAbierto() != incidentesList.get(j).estaAbierto()) {
                    //chequeo 24hs
                    Duration duration = Duration.between(incidentesList.get(i).getFechaHoraApertura(), incidentesList.get(j).getFechaHoraApertura()).abs();
                    if(duration.toHours() < 24) {
                        incidentesList.remove(j);
                    }
                }
            }
        }
        return incidentesList;
    }

    public List<Incidente> filtrarIncidentesSemanales(List<Incidente> incidentes) {
        return incidentes.stream()
                .filter(incidente -> incidente.getFechaHoraApertura().isAfter(LocalDateTime.now().minusDays(7)))
                .toList();
    }
}

