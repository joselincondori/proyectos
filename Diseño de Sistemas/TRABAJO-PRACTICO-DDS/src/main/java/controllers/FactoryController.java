package controllers;

public class FactoryController {
    public static Object controller(String nombre) {
        Object controller = null;

        switch (nombre) {
            case "comunidades": controller = new ComunidadesController(); break;
            case "incidentes": controller = new IncidentesController(); break;
            case "rankings": controller = new RankingsController(); break;
            case "login": controller = new LogInController(); break;
            case "perfil": controller = new PerfilController(); break;
            case "entidadesPrestadoras": controller = new EntidadPrestadoraController(); break;
            case "organismos": controller = new OrganismoDeControlController(); break;
            case "servicios": controller = new ServiciosController(); break;
            case "entidades": controller = new EntidadesController(); break;
            case "miembros": controller = new MiembroController(); break;
            case "administradorPlataforma": controller = new AdministradorPlataformaController(); break;
            case "establecimientos": controller = new EstablecimientoController(); break;
            case "servicio3": controller = new Servicio3APIController(); break;
            case "servicio2": controller = new Servicio2APIController(); break;
            case "prestaciones": controller = new PrestacionServicioController(); break;
            case "usuarios": controller = new UsuarioController(); break;

        }

        return controller;
    }
}
