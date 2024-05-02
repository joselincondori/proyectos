package models.entities.exportadores.estrategiaExportacion.PDF;

import models.entities.exportadores.Exportable;
import models.entities.exportadores.estrategiaExportacion.EstrategiaExportacion;

public class ExportadorPDF implements EstrategiaExportacion {
    public AdapterExportadorPDF adapter;
    @Override
    public String exportar(Exportable exportable) {
        return adapter.exportar(exportable);
    }
}
