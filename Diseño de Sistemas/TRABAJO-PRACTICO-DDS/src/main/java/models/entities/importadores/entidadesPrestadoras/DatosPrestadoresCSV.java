package models.entities.importadores.entidadesPrestadoras;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatosPrestadoresCSV {
    private String nombrePrestador;
    private String nombreRepresentante;
    private String tipo;                    //para separar entre "Entidad Prestadora" y "Organismo de control"
    private String apellidoRepresentante;
    private String usuarioRepresentante;
    private String mailRepresentante;
    private String telRepresentante;

    public DatosPrestadoresCSV(String nombrePrestador, String nombreUsuario, String tipo, String apellidoUsuario, String usuarioRepresentante, String mailUsuario) {
        this.nombrePrestador = nombrePrestador;
        this.nombreRepresentante = nombreUsuario;
        this.tipo = tipo;
        this.apellidoRepresentante = apellidoUsuario;
        this.usuarioRepresentante = usuarioRepresentante;
        this.mailRepresentante = mailUsuario;
    }
}
