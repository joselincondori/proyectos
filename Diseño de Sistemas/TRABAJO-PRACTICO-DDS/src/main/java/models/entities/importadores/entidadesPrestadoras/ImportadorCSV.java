package models.entities.importadores.entidadesPrestadoras;

import models.entities.domain.entidades.EntidadPrestadora;
import models.entities.domain.entidades.OrganismoDeControl;

import java.util.ArrayList;
import java.util.List;

public class ImportadorCSV {
    public List<EntidadPrestadora> importarEntidadesPrestadoras(String archivoCSV) {
        ImportacionPrestadoresCSV importador = new ImportacionPrestadoresCSV(archivoCSV);
        List<DatosPrestadoresCSV> lineas = importador.importar();
        List<EntidadPrestadora> entidadesPrestadoras = new ArrayList<>();

        for(DatosPrestadoresCSV linea : lineas) {
            if (linea.getTipo().equals("Entidad prestadora")){
                EntidadPrestadora nuevaEntidadPrestadora = new ParserCSV().instanciarEntidadPrestadora(linea);
                entidadesPrestadoras.add(nuevaEntidadPrestadora);
            }
        }

        return entidadesPrestadoras;
    }

    public List<OrganismoDeControl> importarOrganismosDeControl(String archivoCSV) {
        ImportacionPrestadoresCSV importador = new ImportacionPrestadoresCSV(archivoCSV);
        List<DatosPrestadoresCSV> lineas = importador.importar();
        List<OrganismoDeControl> organismosDeControl = new ArrayList<>();

        for(DatosPrestadoresCSV linea : lineas) {
            if (linea.getTipo().equals("Organismo de control")){
                OrganismoDeControl nuevoOrganismoDeControl = new ParserCSV().instanciarOrganismoDeControl(linea);
                organismosDeControl.add(nuevoOrganismoDeControl);
            }
        }

        return organismosDeControl;
    }
}
