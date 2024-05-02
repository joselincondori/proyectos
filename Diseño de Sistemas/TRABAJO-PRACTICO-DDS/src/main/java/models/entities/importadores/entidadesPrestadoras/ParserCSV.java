package models.entities.importadores.entidadesPrestadoras;

import models.entities.domain.entidades.EntidadPrestadora;
import models.entities.domain.entidades.OrganismoDeControl;
import models.entities.domain.persona.Persona;
import models.entities.sesion.Usuario;

public class ParserCSV {
    public EntidadPrestadora instanciarEntidadPrestadora(DatosPrestadoresCSV linea) {
        return new EntidadPrestadora(linea.getNombrePrestador(), new Persona(new Usuario(linea.getUsuarioRepresentante(), null),linea.getNombreRepresentante(), linea.getApellidoRepresentante(), linea.getMailRepresentante(), linea.getTelRepresentante(), null));
    }

    public OrganismoDeControl instanciarOrganismoDeControl(DatosPrestadoresCSV linea) {
        return new OrganismoDeControl(linea.getNombrePrestador(), new Persona(new Usuario(linea.getUsuarioRepresentante(), null), linea.getNombreRepresentante(), linea.getApellidoRepresentante(), linea.getMailRepresentante(), linea.getTelRepresentante(), null));
    }
}
