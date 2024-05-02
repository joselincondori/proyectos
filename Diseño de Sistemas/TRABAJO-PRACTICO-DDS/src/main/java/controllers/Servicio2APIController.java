package controllers;

import controllers.dtos.servicio2.response.ConfianzaComunidadResponseDto;
import controllers.dtos.servicio2.response.ConfianzaPersonaResponseDto;
import io.javalin.http.Context;
import models.entities.domain.comunidad.Comunidad;
import models.entities.domain.persona.Persona;
import models.repositories.ComunidadRepository;
import models.repositories.PersonaRepository;
import servicios.servicio2Api.Servicio2API;

import java.io.IOException;

public class Servicio2APIController {
    private final PersonaRepository personaRepository = new PersonaRepository();
    private final ComunidadRepository comunidadRepository = new ComunidadRepository();
    private final Servicio2API servicio2API = new Servicio2API();

    public void pruebaPersona(Context context) {
        try {
            Persona persona = (Persona) personaRepository.findById(1L);
            ConfianzaPersonaResponseDto response = servicio2API.getConfianzaPersona(persona);
            context.status(200);
            context.json(response);
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
            context.status(500);
        }
    }

    public void pruebaComunidad(Context context) {
        try {
            Comunidad comunidad = (Comunidad) comunidadRepository.findById(1L);
            ConfianzaComunidadResponseDto response = servicio2API.getConfianzaComunidad(comunidad);
            context.status(200);
            context.json(response);
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            context.status(500);
        }
    }
}
