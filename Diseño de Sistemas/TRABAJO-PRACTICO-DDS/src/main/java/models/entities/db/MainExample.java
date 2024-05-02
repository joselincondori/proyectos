package models.entities.db;

import io.github.flbulgarelli.jpa.extras.perthread.PerThreadEntityManagerProperties;
import models.entities.domain.comunidad.Comunidad;
import models.entities.domain.comunidad.Miembro;
import models.entities.domain.entidades.*;

import models.entities.domain.georef.Localidad;
import models.entities.domain.georef.Municipio;
import models.entities.domain.georef.Provincia;
import models.entities.domain.incidentes.Incidente;
import models.entities.domain.notificaciones.estrategia.Email.EstrategiaEmail;
import models.entities.domain.notificaciones.tiempo.Inmediata;
import models.entities.domain.persona.ConfigNotificacion;
import models.entities.domain.persona.Persona;
import models.entities.domain.georef.Ubicacion;
import models.entities.domain.roles.Rol;
import models.entities.domain.servicio.PrestacionServicio;
import models.entities.domain.servicio.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import models.entities.importadores.servicioGeoRef.ImportadorGeoRef;
import models.entities.sesion.Usuario;

import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MainExample implements WithSimplePersistenceUnit {

    public static void main(String[] args) {
        configureEntityManagerProperties();
        new MainExample().start();
    }

    private void start() {

//        ImportadorGeoRef.getInstance().importar();

        TipoEntidad tipoBanco = new TipoEntidad("banco");
        TipoEntidad tipoSubte = new TipoEntidad("subte");
        TipoEstablecimiento tipoEstacion = new TipoEstablecimiento("estacion");
        TipoEstablecimiento tipoSucursal = new TipoEstablecimiento("sucursal");

        Entidad entidadBanco = new Entidad("santander", tipoBanco, null, null, null);

        Entidad entidadSubte = new Entidad("subtes argentinos", tipoSubte, null, null, null);

        Establecimiento sucursalBanco = new Establecimiento("sucursal1", new Ubicacion(10,10), tipoSucursal, entidadBanco);
        Establecimiento estacionSubte1 = new Establecimiento("estacion1", new Ubicacion(12,12), tipoEstacion, entidadSubte);
        Establecimiento estacionSubte2 = new Establecimiento("estacion2", new Ubicacion(13,13), tipoEstacion, entidadSubte);

        entidadBanco.agregarEstablecimientos(sucursalBanco);
        entidadSubte.agregarEstablecimientos(estacionSubte1, estacionSubte2);

        // creo roles
        Rol rolAdminPlataforma = new Rol("admin_plataforma");
        Rol rolAdminComunidad = new Rol("admin_comunidad");

        Inmediata inmediata = new Inmediata();
        EstrategiaEmail estrategiaEmail = new EstrategiaEmail();
        ConfigNotificacion configNotificacion1 = new ConfigNotificacion(inmediata, estrategiaEmail);

        // creo personas
        Usuario usuario1 = new Usuario("juancito1", "askjdhfkjashf2323");
        Persona persona1 = new Persona(usuario1, "juancito1", "juan", "juavazquez@frba.utn.edu.ar", "11112222333", configNotificacion1);
        persona1.setConfigNotificacion(configNotificacion1);
        Usuario usuario2 = new Usuario("juancito2", "askjdhfkjashf2323");
        Persona persona2 = new Persona(usuario2, "juancito2", "juan", "j2@mail.com", "11122222333", configNotificacion1);
        Usuario usuario3 = new Usuario("juancito3", "askjdhfkjashf2323");
        Persona persona3 = new Persona(usuario3, "juancito3", "juan", "j3@mail.com", "11122223333", configNotificacion1);

        // creo miembros y comunidades
        Comunidad comunidad = new Comunidad("comunidad1");
        Miembro miembro1 = new Miembro(comunidad, persona1, true);
        Miembro miembro2 = new Miembro(comunidad, persona2, true);
        Miembro miembro3 = new Miembro(comunidad, persona3, true);

        // prestaciones de servicio
        Servicio banio = new Servicio("banio", "banio de hombre");
        Servicio escalera = new Servicio("escalera mecaninca", "escalera mecaninca");
        PrestacionServicio prestacionServicio1 = new PrestacionServicio("banio de estacionSubte1", estacionSubte1, banio);
        PrestacionServicio prestacionServicio2 = new PrestacionServicio("banio de estacionSubte2", estacionSubte2, banio);
        PrestacionServicio prestacionServicio4 = new PrestacionServicio("escalera de la estacionSubte2", estacionSubte2, escalera);
        PrestacionServicio prestacionServicio3 = new PrestacionServicio("banio del banco", sucursalBanco, banio);

        //incidentes
        Incidente incidente1 = new Incidente(comunidad, "nada", prestacionServicio1, persona1);
        Incidente incidente2 = new Incidente(comunidad, "nada", prestacionServicio3, persona2);
        Incidente incidente3 = new Incidente(comunidad, "nada", prestacionServicio2, persona1);
        Incidente incidente4 = new Incidente(comunidad, "nada", prestacionServicio3, persona3);

        incidente1.setFechaHoraApertura(LocalDateTime.now());
        incidente1.cerrar(persona3);
        incidente2.setFechaHoraApertura(LocalDateTime.now());
        incidente2.cerrar(persona2);
        incidente3.setFechaHoraApertura(LocalDateTime.now());
        incidente3.cerrar(persona1);
        incidente4.setFechaHoraApertura(LocalDateTime.now());


        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager().persist(tipoBanco);
        entityManager().persist(tipoSubte);
        entityManager().persist(tipoEstacion);
        entityManager().persist(tipoSucursal);
        entityManager().persist(sucursalBanco);
        entityManager().persist(estacionSubte1);
        entityManager().persist(estacionSubte2);
        entityManager().persist(entidadBanco);
        entityManager().persist(entidadSubte);
        entityManager().persist(comunidad);
        entityManager().persist(inmediata);
        entityManager().persist(configNotificacion1);
        entityManager().persist(usuario1);
        entityManager().persist(usuario2);
        entityManager().persist(usuario3);
        entityManager().persist(persona1);
        entityManager().persist(persona2);
        entityManager().persist(persona3);
        entityManager().persist(miembro1);
        entityManager().persist(miembro2);
        entityManager().persist(miembro3);
        entityManager().persist(rolAdminPlataforma);
        entityManager().persist(rolAdminComunidad);
        entityManager().persist(banio);
        entityManager().persist(escalera);
        entityManager().persist(prestacionServicio1);
        entityManager().persist(prestacionServicio2);
        entityManager().persist(prestacionServicio3);
        entityManager().persist(prestacionServicio4);
        entityManager().persist(incidente1);
        entityManager().persist(incidente2);
        entityManager().persist(incidente3);
        entityManager().persist(incidente4);

        tx.commit();
    }

    public static void configureEntityManagerProperties() {
        // https://stackoverflow.com/questions/8836834/read-environment-variables-in-persistence-xml-file
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<>();

        String[] keys = new String[] {
                "hibernate.archive.autodetection",
                "hibernate.connection.driver_class",
                "hibernate.connection.url",
                "hibernate.connection.username",
                "hibernate.connection.password",
                "hibernate.dialect",
                "hibernate.show_sql",
                "hibernate.format_sql",
                "use_sql_comments",
                "hibernate.hbm2ddl.auto"};

        for (String key : keys) {
            if (env.containsKey(key)) {
                String value = env.get(key);
                System.out.println(key + ": " + value);
                configOverrides.put(key, value);
            }
        }
        Consumer<PerThreadEntityManagerProperties> propertiesConsumer = perThreadEntityManagerProperties -> {
            perThreadEntityManagerProperties.putAll(configOverrides);
        };

        WithSimplePersistenceUnit.configure(propertiesConsumer);
    }
}