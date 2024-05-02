package models.entities.exportadores.estrategiaExportacion.PDF;

import models.entities.exportadores.Exportable;

public interface AdapterExportadorPDF {
    public String exportar(Exportable exportable);
}
