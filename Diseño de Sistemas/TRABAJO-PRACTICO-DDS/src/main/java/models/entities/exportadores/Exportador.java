package models.entities.exportadores;

import models.entities.exportadores.estrategiaExportacion.EstrategiaExportacion;
import lombok.Getter;
import lombok.Setter;

public class Exportador {

    private static Exportador instancia;

    @Getter
    @Setter
    private EstrategiaExportacion estrategia;

    public static Exportador getInstancia() {
        if (instancia == null) {
            instancia = new Exportador();
        }
        return instancia;
    }

    public String exportar(Exportable exportable) {
        return estrategia.exportar(exportable);
    }




}
