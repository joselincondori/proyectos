package server;

import controllers.*;
import server.utils.TipoRol;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Router {
    public static void init() {
        // HTTP -> GET - POST - PUT - PATCH - DELETE

        Server.app().routes(() -> {
            // INDEX
            path("", () -> {
                get(((LogInController) FactoryController.controller("login"))::index);
            });

            // LOG IN
            path("login", () -> {
                get(((LogInController) FactoryController.controller("login"))::login);
                post(((LogInController) FactoryController.controller("login"))::validar);
            });

            // SIGN UP
            path("signup", () -> {
                get(((LogInController) FactoryController.controller("login"))::create);
                post(((LogInController) FactoryController.controller("login"))::save);
            });


            // CERRAR SESION
            path("logout", () -> {
                get(((LogInController) FactoryController.controller("login"))::logout);
            });



            // UBICACIONES
            path("provincias/{id}/municipios", () -> {
                get(((AdministradorPlataformaController) FactoryController.controller("administradorPlataforma"))::getMunicipiosDeProvincia);
            });
            path("municipios/{id}/localidades", () -> {
                get(((AdministradorPlataformaController) FactoryController.controller("administradorPlataforma"))::getLocalidadesDeMunicipio);
            });

            // ESTABLECIMIENTOS DE ENTIDADES
            path("entidades", () -> {
                get("{id}/establecimientos", ((EntidadesController) FactoryController.controller("entidades"))::getEstablecimientosByEntidadId);
            });

            // PRESTACIONES DE ESTABLECIMIENTOS
            path("establecimientos", () -> {
                get("{id}/servicios", ((EntidadesController) FactoryController.controller("entidades"))::getPrestacionesDeEstablecimientoByEstablecimientoId);
            });

            path("app", () -> {
                // PORTAL
                path("portal", () -> {
                    get(((LogInController) FactoryController.controller("login"))::show);
                });

                //PERFIL
                path("perfil", () -> {
                    get(((PerfilController) FactoryController.controller("perfil"))::perfil);
                    post(((PerfilController) FactoryController.controller("perfil"))::guardarCambios);
                });

                // RANKINGS
                path("rankings", () -> {
                    get(((RankingsController) FactoryController.controller("rankings"))::rankings);

                    get("r1", ((RankingsController) FactoryController.controller("rankings"))::ranking1);
                    get("r2", ((RankingsController) FactoryController.controller("rankings"))::ranking2);
                    get("r3", ((RankingsController) FactoryController.controller("rankings"))::ranking3);
                });

                // INCIDENTES
                path("incidentes", () -> {
                    post("cerrar", ((IncidentesController) FactoryController.controller("incidentes"))::cerrarIncidente);
                    get("cerrar/{id}", ((IncidentesController) FactoryController.controller("incidentes"))::requestCerrarIncidente);
                    get("abrir", ((IncidentesController) FactoryController.controller("incidentes"))::abrirIncidenteForm);
                    post("abrir", ((IncidentesController) FactoryController.controller("incidentes"))::abrirIncidente);
                    get("sugerencias/gracias", ((IncidentesController) FactoryController.controller("incidentes"))::sugerenciaRevisionResueltaGracias);
                    post("sugerencias", ((IncidentesController) FactoryController.controller("incidentes"))::sugerenciaRevision);
                    get("sugerencias", ((IncidentesController) FactoryController.controller("incidentes"))::mostrarSugerencias);
                    post("sugerencias/resolver", ((IncidentesController) FactoryController.controller("incidentes"))::resolverSugerenciaRevisionSegunBoton);
                    get("sugerencias/actualizar", ((IncidentesController) FactoryController.controller("incidentes"))::enviarSugerenciasRevision, TipoRol.ADMINISTRADOR_PLATAFORMA);
                });

                // ENTIDADES - ESTABLECIMIENTOS
                path("entidades", () -> {
                    get(((EntidadesController) FactoryController.controller("entidades"))::index, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("{id}/delete",((EntidadesController) FactoryController.controller("entidades"))::delete, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("{id}/edit",((EntidadesController) FactoryController.controller("entidades"))::edit, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    post("{id}/edit",((EntidadesController) FactoryController.controller("entidades"))::update, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("crear",((EntidadesController) FactoryController.controller("entidades"))::create, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    post("crear",((EntidadesController) FactoryController.controller("entidades"))::save, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("{id}/establecimientos/edit",((EntidadesController) FactoryController.controller("entidades"))::editEstablecimientos, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("{id}/establecimientos/crear",((EstablecimientoController) FactoryController.controller("establecimientos"))::create, TipoRol.ADMINISTRADOR_PLATAFORMA);
                });

                path("establecimientos", () -> {
                    post("crear", ((EstablecimientoController) FactoryController.controller("establecimientos"))::save, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("{id}/edit", ((EstablecimientoController) FactoryController.controller("establecimientos"))::edit, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    post("{id}/edit",((EstablecimientoController) FactoryController.controller("establecimientos"))::update, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("{id}/delete", ((EstablecimientoController) FactoryController.controller("establecimientos"))::delete, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("{id}/prestaciones/edit", ((PrestacionServicioController) FactoryController.controller("prestaciones"))::editPrestaciones, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("{id}/prestaciones/crear", ((PrestacionServicioController) FactoryController.controller("prestaciones"))::create, TipoRol.ADMINISTRADOR_PLATAFORMA);
                });

                path("prestaciones", () -> {
                    get("{id}/edit",((PrestacionServicioController) FactoryController.controller("prestaciones"))::edit, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    post("{id}/edit",((PrestacionServicioController) FactoryController.controller("prestaciones"))::update, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    post("crear",((PrestacionServicioController) FactoryController.controller("prestaciones"))::save, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    post("eliminar",((PrestacionServicioController) FactoryController.controller("prestaciones"))::delete, TipoRol.ADMINISTRADOR_PLATAFORMA);
                });

                // SERVICIOS
                path("servicios", () -> {
                    get(((ServiciosController) FactoryController.controller("servicios"))::index, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    post(((ServiciosController) FactoryController.controller("servicios"))::save, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("{id}/edit",((ServiciosController) FactoryController.controller("servicios"))::edit, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("{id}/delete",((ServiciosController) FactoryController.controller("servicios"))::delete, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("create",((ServiciosController) FactoryController.controller("servicios"))::create, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    post("{id}/edit",((ServiciosController) FactoryController.controller("servicios"))::update, TipoRol.ADMINISTRADOR_PLATAFORMA);
                });

                // ADMINISTRADOR PLATAFORMA
                path("admin-plataforma", ()->{
                    get("usuarios", ((UsuarioController) FactoryController.controller("usuarios"))::index, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    post("usuarios/desactivar",((UsuarioController) FactoryController.controller("usuarios"))::desactivarUsuario, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    post("usuarios/activar",((UsuarioController) FactoryController.controller("usuarios"))::activarUsuario, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get( ((AdministradorPlataformaController) FactoryController.controller("administradorPlataforma"))::index, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("organismos", ((OrganismoDeControlController) FactoryController.controller("organismos"))::index, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("comunidades", ((ComunidadesController) FactoryController.controller("comunidades"))::index, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("establecimientos", ((EstablecimientoController) FactoryController.controller("establecimientos"))::index, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("entidades-prestadoras", ((EntidadPrestadoraController) FactoryController.controller("entidadesPrestadoras"))::index, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("servicios", ((ServiciosController) FactoryController.controller("servicios"))::index, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("entidades", ((EntidadesController) FactoryController.controller("entidades"))::index, TipoRol.ADMINISTRADOR_PLATAFORMA);
                });

                // ENTIDADES PRESTADORAS
                path("entidades-prestadoras", ()->{
                    get(((EntidadPrestadoraController) FactoryController.controller("entidadesPrestadoras"))::index, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("carga-masiva-entidades", ((EntidadPrestadoraController) FactoryController.controller("entidadesPrestadoras"))::cargaMasiva, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    post("carga-masiva-entidades", ((EntidadPrestadoraController) FactoryController.controller("entidadesPrestadoras"))::procesarCargaMasiva, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("{id}/entidades",((EntidadesController) FactoryController.controller("entidades"))::entidadesEntidadPrestadora, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    post("{id}/{idPersona}/{idUsuario}",((EntidadPrestadoraController) FactoryController.controller("entidadesPrestadoras"))::update, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("create",((EntidadPrestadoraController) FactoryController.controller("entidadesPrestadoras"))::create, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    post(((EntidadPrestadoraController) FactoryController.controller("entidadesPrestadoras"))::save, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("{id}/edit",((EntidadPrestadoraController) FactoryController.controller("entidadesPrestadoras"))::edit, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("{idOrganismo}/entidades-create",((EntidadPrestadoraController) FactoryController.controller("entidadesPrestadoras"))::createEntidadDeOrganismo, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("{id}/delete",((EntidadPrestadoraController) FactoryController.controller("entidadesPrestadoras"))::delete, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("{id}/entidades/agregar", ((EntidadPrestadoraController) FactoryController.controller("entidadesPrestadoras"))::agregarEntidadesView);
                    post("entidades/agregar", ((EntidadesController) FactoryController.controller("entidades"))::agregarEntidadAEntidadPrestadora);
                    post("entidades/sacar", ((EntidadesController) FactoryController.controller("entidades"))::eliminarEntidadDeLaEntidadPrestadora);
                });

                // ORGANISMOS DE CONTROL
                path("organismos", ()->{
                    get("create",((OrganismoDeControlController) FactoryController.controller("organismos"))::create, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("carga-masiva", ((OrganismoDeControlController) FactoryController.controller("organismos"))::cargaMasiva, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    post("carga-masiva", ((OrganismoDeControlController) FactoryController.controller("organismos"))::procesarCargaMasiva, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("{id}",((OrganismoDeControlController) FactoryController.controller("organismos"))::show, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("{id}/edit",((OrganismoDeControlController) FactoryController.controller("organismos"))::edit, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("{id}/delete",((OrganismoDeControlController) FactoryController.controller("organismos"))::delete, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    post("{id}/{idPersona}/{idUsuario}",((OrganismoDeControlController) FactoryController.controller("organismos"))::update, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    post(((OrganismoDeControlController) FactoryController.controller("organismos"))::save, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("{id}/servicios",((ServiciosController) FactoryController.controller("servicios"))::serviciosDelOrganismo, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("{id}/servicios-edit",((OrganismoDeControlController) FactoryController.controller("organismos"))::editServicios, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    post("servicios/sacar", ((ServiciosController) FactoryController.controller("servicios"))::eliminarServicioDeOrganismo, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("{id}/servicios/agregar", ((OrganismoDeControlController) FactoryController.controller("organismos"))::agregarServicioView, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    post("servicios/agregar", ((ServiciosController) FactoryController.controller("servicios"))::agregarServicioAOrganismo, TipoRol.ADMINISTRADOR_PLATAFORMA);
                });

                // COMUNIDADES
                path("/comunidades", ()->{
                    get(((ComunidadesController) FactoryController.controller("comunidades"))::comunidadesUsuario);
                    post("miembro/delete",((ComunidadesController) FactoryController.controller("comunidades"))::eliminarMiembroComunidad);
                    post("miembro/sacar-admin",((ComunidadesController) FactoryController.controller("comunidades"))::sacarAdminMiembro, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    post("miembro/dar-admin",((ComunidadesController) FactoryController.controller("comunidades"))::hacerAdminMiembro, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("unirse",((ComunidadesController) FactoryController.controller("comunidades"))::comunidadesParaUnirse);
                    get("{id}/miembros",((MiembroController) FactoryController.controller("miembros"))::index);
                    get("{id}/incidentes",((IncidentesController) FactoryController.controller("incidentes"))::incidentes);
                    get("{id}/edit",((ComunidadesController) FactoryController.controller("comunidades"))::edit, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("{id}/delete",((ComunidadesController) FactoryController.controller("comunidades"))::delete, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("create",((ComunidadesController) FactoryController.controller("comunidades"))::create, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    post("{id}",((ComunidadesController) FactoryController.controller("comunidades"))::update, TipoRol.ADMINISTRADOR_PLATAFORMA);
                    get("{id}/salir",((ComunidadesController) FactoryController.controller("comunidades"))::salirDeComunidad);
                    get("{id}/unirse",((ComunidadesController) FactoryController.controller("comunidades"))::unirseAComunidad);
                    post(((ComunidadesController) FactoryController.controller("comunidades"))::save);
                });


                // MIEMBROS
                path("/miembros", ()->{
                    get("{id}/edit",((MiembroController) FactoryController.controller("miembros"))::edit, TipoRol.ADMINISTRADOR_PLATAFORMA);
                });

                //INTERESES
                path("/intereses", ()->{
                    get(((PerfilController) FactoryController.controller("perfil"))::intereses);
                    get("/entidades/agregar", ((PerfilController) FactoryController.controller("perfil"))::agregarEntidadView);
                    get("/servicios/agregar", ((PerfilController) FactoryController.controller("perfil"))::agregarServicioView);
                    post("servicios/agregar", ((ServiciosController) FactoryController.controller("servicios"))::agregarServicioAUsuario);
                    post("entidades/agregar", ((EntidadesController) FactoryController.controller("entidades"))::agregarEntidadAUsuario);

                });

                // SERVICIO 3 API
                path("/servicio3", ()->{
                    get("ranking",((Servicio3APIController) FactoryController.controller("servicio3"))::pruebaRanking);
                });

                // SERVICIO 2 API
                path("/servicio2", ()->{
                    get("persona",((Servicio2APIController) FactoryController.controller("servicio2"))::pruebaPersona);
                    get("comunidad",((Servicio2APIController) FactoryController.controller("servicio2"))::pruebaComunidad);
                });
            });
        });
    }
}
