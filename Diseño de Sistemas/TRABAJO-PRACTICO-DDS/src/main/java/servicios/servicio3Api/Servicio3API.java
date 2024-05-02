package servicios.servicio3Api;

import config.Config;
import controllers.dtos.servicio3.request.ComunidadDto;
import controllers.dtos.servicio3.request.EntidadDto;
import controllers.dtos.servicio3.request.IncidenteDto;
import controllers.dtos.servicio3.request.RankingRequestDto;
import controllers.dtos.servicio3.response.EntradaRankingDto;
import controllers.dtos.servicio3.response.RankingGeneradoDto;
import models.entities.domain.comunidad.Comunidad;
import models.entities.domain.entidades.Entidad;
import models.entities.domain.entidades.Establecimiento;
import models.entities.domain.incidentes.Incidente;
import models.entities.domain.rankings.EntradaRanking;
import models.entities.domain.rankings.RankingGenerado;
import models.repositories.ComunidadRepository;
import models.repositories.EntidadRepository;
import models.repositories.IncidenteRepository;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servicios.Servicio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Servicio3API extends Servicio {
    private final IncidenteRepository incidenteRepository = new IncidenteRepository();
    private final EntidadRepository entidadRepository = new EntidadRepository();
    private final ComunidadRepository comunidadRepository = new ComunidadRepository();
    private static final Logger log = LoggerFactory.getLogger(Servicio3API.class);

    public RankingGenerado getRanking() throws IOException {
        String url = Config.obtenerInstancia().obtenerDelConfig("servicio3-url");
        log.info("url? " + url);
        // creo el ranking request dto
        RankingRequestDto rankingRequestDto = generarRankingRequestDto();

        // mando el post
        Response response = sendPost(url, rankingRequestDto);
        log.info("response is successful? " + response.isSuccessful());
        if(response.isSuccessful()) {
            String responseJson = response.body().string();
            RankingGeneradoDto rankingGeneradoDto = objectMapper.readValue(responseJson, RankingGeneradoDto.class);
            RankingGenerado rankingGenerado = generarRankingConDto(rankingGeneradoDto);
            return rankingGenerado;
        } else
            log.error("Runtime Exception");
            throw new RuntimeException();
    }

    public RankingGeneradoDto getRankingDto() throws IOException {
        String url = Config.obtenerInstancia().obtenerDelConfig("servicio3-url");

        // creo el ranking request dto
        RankingRequestDto rankingRequestDto = generarRankingRequestDto();

        // mando el post
        Response response = sendPost(url, rankingRequestDto);

        if(response.isSuccessful()) {
            String responseJson = response.body().string();
            RankingGeneradoDto rankingGeneradoDto = objectMapper.readValue(responseJson, RankingGeneradoDto.class);
            return rankingGeneradoDto;
        } else
            throw new RuntimeException();
    }

    private RankingGenerado generarRankingConDto(RankingGeneradoDto rankingGeneradoDto) {
        RankingGenerado rankingGenerado = new RankingGenerado(rankingGeneradoDto.getDescripcion());
        for(EntradaRankingDto entradaDto : rankingGeneradoDto.getRanking()) {
            Entidad entidad = (Entidad) entidadRepository.findById(entradaDto.getEntidadId());
            EntradaRanking entrada = new EntradaRanking(entradaDto.getPuesto(), entradaDto.getPuntaje(), entidad);
            rankingGenerado.agregarEntrada(entrada);
        }
        return rankingGenerado;
    }

    private RankingRequestDto generarRankingRequestDto() {
        List<ComunidadDto> comunidadDtos = new ArrayList<>();
        List<EntidadDto> entidadDtos = new ArrayList<>();
        RankingRequestDto rankingRequestDto = null;
        try {
            List<Comunidad> comunidades = (List<Comunidad>) comunidadRepository.findAll();
            for(Comunidad comunidad : comunidades) {
                List<Incidente> incidentes = incidenteRepository.getIncidentesDeComunidad(comunidad);
                List<IncidenteDto> incidenteDtos = new ArrayList<>();
                for(Incidente i : incidentes) {
                    IncidenteDto incidenteDto = new IncidenteDto(i.getFechaHoraApertura(), i.getFechaHoraCierre(), i.getIdEstablecimiento());
                    incidenteDtos.add(incidenteDto);
                }
                ComunidadDto comunidadDto = new ComunidadDto(comunidad.calcularNumeroAfectados(), incidenteDtos);
                comunidadDtos.add(comunidadDto);
                log.info("Comunidad: " + comunidad.getNombre());
            }

            List<Entidad> entidades = (List<Entidad>) entidadRepository.findAll();
            for(Entidad e : entidades) {
                List<Long> idEstablecimientos = e.getEstablecimientos().stream().map(Establecimiento::getId).toList();
                EntidadDto entidadDto = new EntidadDto(e.getId(), e.getNombre(), idEstablecimientos);
                entidadDtos.add(entidadDto);
                log.info("Entidad: " + e.getNombre());
            }
            rankingRequestDto = new RankingRequestDto(comunidadDtos, entidadDtos, 25f);

        } catch (NullPointerException e) {
            log.error(e.getMessage());
            System.out.println( e.getMessage());
        }

        return rankingRequestDto;
    }
}
