package rankings;

import models.entities.domain.comunidad.Comunidad;
import models.entities.domain.comunidad.Miembro;
import models.entities.domain.entidades.Entidad;
import models.entities.domain.entidades.Establecimiento;
import models.entities.domain.incidentes.Incidente;
import models.entities.domain.persona.Persona;
import models.entities.domain.rankings.RankingEntidadesMayorCantIncidentes;
import models.entities.domain.rankings.RankingGenerado;
import models.entities.domain.servicio.PrestacionServicio;
import models.entities.domain.servicio.Servicio;
import models.repositories.EntidadRepository;
import models.repositories.IncidenteRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class rankingsTests {
    @Test
    public void rankingEntidadesMayorCantidadIncidentes() {
        Entidad e1 = new Entidad("LineaRoca",null, null, null, null);
        Entidad e2 = new Entidad("LineaA",null, null, null, null);
        Entidad e3 = new Entidad("LineaB",null, null,null, null);

        Comunidad c1 = new Comunidad("c1");
        Persona juan = new Persona(null, "Juan", "Perez", null, null, null);
        Miembro m1 = new Miembro(c1, juan, true);

        Establecimiento establecimiento = new Establecimiento("e1",null,null, e1);
        e1.agregarEstablecimientos(establecimiento);
        Establecimiento establecimiento2 = new Establecimiento("e2",null,null, e2);
        e2.agregarEstablecimientos(establecimiento2);
        Establecimiento establecimiento3 = new Establecimiento("e3",null,null, e3);
        e3.agregarEstablecimientos(establecimiento3);

        PrestacionServicio banioHombre = new PrestacionServicio("", establecimiento, null);
        PrestacionServicio banioMujer = new PrestacionServicio("", establecimiento, null);
        PrestacionServicio escalera = new PrestacionServicio("", establecimiento2, null);
        PrestacionServicio escalera2 = new PrestacionServicio("", establecimiento3, null);
        PrestacionServicio ascensor = new PrestacionServicio("", establecimiento3, null);
        PrestacionServicio ps = new PrestacionServicio("", establecimiento3, null);

        // Establecimiento - 2 || Establecimiento2 - 1 || Establecimiento3 - 3

        Incidente inc1 = new Incidente(c1,"", banioHombre, juan);
        inc1.setFechaHoraApertura(LocalDateTime.of(2023, 10, 13, 15, 30));
        Incidente inc2 = new Incidente(c1,"", banioMujer,juan);
        inc2.setFechaHoraApertura(LocalDateTime.of(2023, 10, 13, 15, 30));
        Incidente inc3 = new Incidente(c1,"", escalera,juan);
        inc3.setFechaHoraApertura(LocalDateTime.of(2023, 10, 13, 15, 30));
        Incidente inc4 = new Incidente(c1,"", escalera2,juan);
        inc4.setFechaHoraApertura(LocalDateTime.of(2023, 10, 13, 15, 30));
        Incidente inc5 = new Incidente(c1,"", ascensor,juan);
        inc5.setFechaHoraApertura(LocalDateTime.of(2023, 10, 13, 15, 30));
        Incidente inc6 = new Incidente(c1,"", ps,juan);
        inc6.setFechaHoraApertura(LocalDateTime.of(2023, 10, 13, 15, 30));


        RankingEntidadesMayorCantIncidentes ranking = new RankingEntidadesMayorCantIncidentes();

        List<Entidad> entidades = new ArrayList<>();
        entidades.add(e1);
        entidades.add(e2);
        entidades.add(e3);

        List<Incidente> incidentes = new ArrayList<>();
        incidentes.add(inc1);
        incidentes.add(inc2);
        incidentes.add(inc3);
        incidentes.add(inc4);
        incidentes.add(inc5);
        incidentes.add(inc6);

        RankingGenerado rankingReal = ranking.generar(entidades,incidentes);

        rankingReal.getRanking().forEach(entrada -> {
            System.out.println(entrada.getPosicion() + " - " + entrada.getEntidad().getNombre() + " - " + entrada.getPuntaje());
        });



    }
    /*
    @Test
    public void rankingIncidentesImpactoComunidades() {
        // Devuelve los servicios.hbs de Comunidad1 Comunidad3 Comunidad2

        Comunidad c1 = new Comunidad("comunidad1");
        Comunidad c2 = new Comunidad("comunidad2");
        Comunidad c3 = new Comunidad("comunidad3");

        Miembro m1 = new Miembro(c1, null);
        Miembro m2 = new Miembro(c1, null);
        Miembro m3 = new Miembro(c1, null);
        Miembro m4 = new Miembro(c2, null);
        Miembro m5 = new Miembro(c3, null);
        Miembro m6 = new Miembro(c3, null);

        Servicio s1 = new Servicio("bañoHombre", "");
        Servicio s2 = new Servicio("escalera", "");
        Servicio s3 = new Servicio("ascensor", "");

        Establecimiento e1 = new Establecimiento("Retiro",null,null);
        Establecimiento e2 = new Establecimiento("Liniers",null,null);
        Establecimiento e3 = new Establecimiento("Nose",null,null);

        PrestacionServicio bañoHombre = new PrestacionServicio("", e1, s1);
        PrestacionServicio escalera = new PrestacionServicio("", e2, s2);
        PrestacionServicio ascensor = new PrestacionServicio("", e3, s3);

        m1.abrirIncidente("", bañoHombre);
        m4.abrirIncidente("", escalera);
        m6.abrirIncidente("", ascensor);

        RankingIncidentesImpactoComunidades ranking = RankingIncidentesImpactoComunidades.getInstance();
        ranking.generar();
    }
     */
}
