package servicios.servicio2Api;

import models.repositories.ComunidadRepository;
import models.repositories.IncidenteRepository;
import models.repositories.PersonaRepository;
import servicios.Servicio;

import config.Config;
import controllers.dtos.servicio2.request.*;
import controllers.dtos.servicio2.response.ConfianzaComunidadResponseDto;
import controllers.dtos.servicio2.response.ConfianzaPersonaResponseDto;
import models.entities.domain.comunidad.Comunidad;
import models.entities.domain.incidentes.Incidente;
import models.entities.domain.persona.Persona;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Servicio2API extends Servicio {
    private final PersonaRepository personaRepository = new PersonaRepository();
    private final ComunidadRepository comunidadRepository = new ComunidadRepository();
    private final IncidenteRepository incidenteRepository = new IncidenteRepository();

    public ConfianzaPersonaResponseDto getConfianzaPersona(Persona persona) throws IOException, RuntimeException {
        String url = Config.obtenerInstancia().obtenerDelConfig("servicio2-url-usuario");

        ConfianzaPersonaRequestDto requestDto = generarPersonaRequestDto(persona);

        // mando el post y recibo respuesta
        Response response = sendPost(url, requestDto);

        if(response.isSuccessful()) {
            String responseJson = response.body().string();
            return objectMapper.readValue(responseJson, ConfianzaPersonaResponseDto.class);
        } else
            throw new RuntimeException();
    }

    public ConfianzaComunidadResponseDto getConfianzaComunidad(Comunidad comunidad) throws IOException, RuntimeException {
        String url = Config.obtenerInstancia().obtenerDelConfig("servicio2-url-comunidad");

        ConfianzaComunidadRequestDto requestDto = generarComunidadRequestDto(comunidad);

        // mando el post y recibo respuesta
        Response response = sendPost(url, requestDto);

        if(response.isSuccessful()) {
            String responseJson = response.body().string();
            return objectMapper.readValue(responseJson, ConfianzaComunidadResponseDto.class);
        } else
            throw new RuntimeException();
    }

    private ConfianzaPersonaRequestDto generarPersonaRequestDto(Persona persona) {
        PersonaDto personaDto = generarPersonaDto(persona);
        List<Incidente> incidentes = incidenteRepository.getIncidentesCerradosDePersona(persona.getId());
        List<IncidenteDto> incidenteDtos = generarIncidentesDto(incidentes);
        return new ConfianzaPersonaRequestDto(personaDto, incidenteDtos);
    }

    private ConfianzaComunidadRequestDto generarComunidadRequestDto(Comunidad comunidad) {
        List<PersonaDto> miembroDtos = comunidad.getMiembros().stream().map(m -> generarPersonaDto(m.getPersona())).toList();
        ComunidadDto comunidadDto = new ComunidadDto(comunidad.getGradoDeConfianza().name(), miembroDtos);
        List<Incidente> incidentes = incidenteRepository.getIncidenteCerradosDeComunidad(comunidad.getId());
        List<IncidenteDto> incidenteDtos = generarIncidentesDto(incidentes);
        return new ConfianzaComunidadRequestDto(comunidadDto, incidenteDtos);
    }

    private PersonaDto generarPersonaDto(Persona persona) {
        return new PersonaDto(persona.getNombre(), persona.getApellido(), persona.getId(), persona.getPuntosConfianza());
    }

    private List<IncidenteDto> generarIncidentesDto(List<Incidente> incidentes) {
        List<IncidenteDto> incidenteDtos = new ArrayList<>();
        for(Incidente i : incidentes) {
            Persona creador = i.getCreador();
            PersonaDto creadorDto = new PersonaDto(creador.getNombre(), creador.getApellido(), creador.getId(), creador.getPuntosConfianza());
            Persona analizador = i.getAnalizador();
            PersonaDto analizadorDto = new PersonaDto(analizador.getNombre(), analizador.getApellido(), analizador.getId(), analizador.getPuntosConfianza());
            ServicioDto servicioDto = new ServicioDto(i.getPrestacionServicio().getServicio().getId());
            IncidenteDto incidenteDto = new IncidenteDto(i.getId(), i.getFechaHoraApertura(), creadorDto, i.getFechaHoraCierre(), analizadorDto, servicioDto);
            incidenteDtos.add(incidenteDto);
        }
        return incidenteDtos;
    }
}
