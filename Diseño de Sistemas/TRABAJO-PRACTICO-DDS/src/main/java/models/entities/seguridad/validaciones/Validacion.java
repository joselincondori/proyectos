package models.entities.seguridad.validaciones;

public interface  Validacion {
        boolean esValida(String usuario, String contrasenia) throws RuntimeException;
}
