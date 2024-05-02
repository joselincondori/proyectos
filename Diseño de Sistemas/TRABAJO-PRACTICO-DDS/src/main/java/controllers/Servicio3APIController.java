package controllers;

import config.Config;
import controllers.dtos.servicio3.request.ComunidadDto;
import controllers.dtos.servicio3.request.EntidadDto;
import controllers.dtos.servicio3.request.IncidenteDto;
import controllers.dtos.servicio3.request.RankingRequestDto;
import controllers.dtos.servicio3.response.EntradaRankingDto;
import controllers.dtos.servicio3.response.RankingGeneradoDto;
import io.javalin.http.Context;
import models.entities.domain.comunidad.Comunidad;
import models.entities.domain.entidades.Entidad;
import models.entities.domain.entidades.Establecimiento;
import models.entities.domain.incidentes.Incidente;
import models.entities.domain.rankings.EntradaRanking;
import models.entities.domain.rankings.RankingGenerado;
import models.repositories.ComunidadRepository;
import models.repositories.EntidadRepository;
import models.repositories.IncidenteRepository;
import okhttp3.*;
import servicios.Servicio;
import servicios.servicio3Api.Servicio3API;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Servicio3APIController {
    private final Servicio3API servicio3API = new Servicio3API();

    public void pruebaRanking(Context context) {
        try {
            RankingGeneradoDto ranking = servicio3API.getRankingDto();
            context.status(200);
            context.json(ranking);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            context.status(500);
        }
    }
}
