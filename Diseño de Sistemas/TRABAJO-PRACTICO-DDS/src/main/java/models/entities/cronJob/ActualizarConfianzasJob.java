package models.entities.cronJob;

import controllers.dtos.servicio2.response.ConfianzaComunidadResponseDto;
import controllers.dtos.servicio2.response.ConfianzaPersonaResponseDto;
import models.entities.domain.comunidad.Comunidad;
import models.entities.domain.persona.GradoDeConfianza;
import models.entities.domain.persona.Persona;
import models.repositories.ComunidadRepository;
import models.repositories.PersonaRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import servicios.servicio2Api.Servicio2API;

import java.io.IOException;
import java.util.List;

public class ActualizarConfianzasJob implements Job {
    private final PersonaRepository personaRepository;
    private final ComunidadRepository comunidadRepository;

    public static String CRON_EXPRESION = "00 13 * * SUN";

    public ActualizarConfianzasJob() {
        this.comunidadRepository = new ComunidadRepository();
        this.personaRepository = new PersonaRepository();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        Servicio2API servicio = new Servicio2API();

        List<Persona> personas = (List<Persona>) personaRepository.findAll();
        if(personas.isEmpty())
            return;
        for (Persona persona : personas) {
            try {
                ConfianzaPersonaResponseDto dto = servicio.getConfianzaPersona(persona);
                actualizarPersona(persona, dto);
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }

        List<Comunidad> comunidades = (List<Comunidad>) comunidadRepository.findAll();
        if(comunidades.isEmpty())
            return;
        for (Comunidad comunidad : comunidades) {
            try {
                ConfianzaComunidadResponseDto dto = servicio.getConfianzaComunidad(comunidad);
                actualizarComunidad(comunidad, dto);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void actualizarPersona(Persona persona, ConfianzaPersonaResponseDto dto) {
        float puntajeNuevo = dto.getNuevoPuntaje();
        GradoDeConfianza gradoDeConfianzaNuevo = GradoDeConfianza.values()[dto.getGradoDeConfianzaActual()];

        persona.setPuntosConfianza(puntajeNuevo);
        persona.setGradoDeConfianza(gradoDeConfianzaNuevo);
    }

    private void actualizarComunidad(Comunidad comunidad, ConfianzaComunidadResponseDto dto) {
        GradoDeConfianza gradoDeConfianzaNuevo = GradoDeConfianza.values()[dto.getGradoDeConfianzaActual()];
        comunidad.setGradoDeConfianza(gradoDeConfianzaNuevo);
    }
}
