package com.servicio3.api.domain.rankings;
import com.servicio3.api.domain.comunidad.Comunidad;
import com.servicio3.api.domain.entidades.Entidad;
import com.servicio3.api.domain.incidentes.Incidente;
import com.servicio3.api.dto.RankingDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class GeneradorRanking {
    /*
        suma total tiempo resolucion incidentes + incidentes no resueltos * CNF
     */
    public RankingGenerado generarRanking(RankingDto rankingDto) {
        List<Entidad> entidades = new ArrayList<>(rankingDto.getEntidades());
        List<Comunidad> comunidades = new ArrayList<>(rankingDto.getComunidades());
        List<Incidente> incidentes = new ArrayList<>();
        for (Comunidad comunidad : comunidades) {
            incidentes.addAll(comunidad.getIncidentes());
        }

        List<EntradaRanking> entradasRankings = new ArrayList<>();

        for(Entidad entidad : entidades) {
            List<Incidente> incidentesEntidad = filtrarIncidentesPertenecientesAEntidad(incidentes, entidad);
            int cantidadAfectadosIncidentes = getAfectadosPorIncidentes(incidentesEntidad, comunidades);
            Float puntaje = (obtenerSumaTiempoResolucion(incidentesEntidad) + cantIncidentesNoResultos(incidentesEntidad) * rankingDto.getCnf()) * cantidadAfectadosIncidentes;
            EntradaRanking entrada = new EntradaRanking(puntaje, entidad.getNombre(), entidad.getId());
            entradasRankings.add(entrada);
        }

        // Se ordenan las entradas segun el puntaje
        entradasRankings = entradasRankings.stream().sorted(Comparator.comparing(EntradaRanking::getPuntaje).reversed()).toList();

        // agrego el puesto a las entradas
        for(int i=0; i < entradasRankings.size(); i++) {
            entradasRankings.get(i).setPuesto(i+1);
        }

        // Se genera el ranking
        RankingGenerado rankingGenerado = new RankingGenerado("ranking");
        rankingGenerado.setRanking(entradasRankings);
        return rankingGenerado;
    }

    // retorna la cantidad de incidentes no resueltos
    private int cantIncidentesNoResultos(List<Incidente> incidentes) {
        return incidentes.stream().filter(Incidente::estaAbierto).toList().size();
    }

    // obtiene la suma de los tiempos de resolucion (en horas) de una lista de incidentes
    private Long obtenerSumaTiempoResolucion(List<Incidente> incidentes) {
        List<Long> numList = incidentes.stream().map(Incidente::calcularDuracion).filter(n -> n > 0).toList();
        return numList.stream().mapToLong(Long::longValue).sum();
    }

    // si alguno de la lista de ids de establecimientos que tiene la entidad machea con el establecimiento del incidente
    public List<Incidente> filtrarIncidentesPertenecientesAEntidad(List<Incidente> incidentes, Entidad entidad) {
        return incidentes.stream()
                .filter(i -> entidad.getIdEstablecimientos().contains(i.getIdEstablecimiento()))
                .toList();
    }

    // retorna la cantidad de miembros afectados por los incidentes
    public int getAfectadosPorIncidentes(List<Incidente> incidentes, List<Comunidad> comunidades) {
        List<Integer> listaAfectados = new ArrayList<>();
        for(Comunidad comunidad : comunidades) {
            for(Incidente incidente : incidentes) {
                if (comunidad.getIncidentes().contains(incidente))
                    listaAfectados.add(comunidad.getCantMiembrosAfectados());
            }
        }
        return listaAfectados.stream().mapToInt(Integer::intValue).sum();
    }
}

