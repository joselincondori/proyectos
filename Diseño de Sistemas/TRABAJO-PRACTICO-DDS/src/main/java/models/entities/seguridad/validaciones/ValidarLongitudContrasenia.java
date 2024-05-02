package models.entities.seguridad.validaciones;

import models.entities.seguridad.excepciones.ExceptionLongitudContrasenia;

public class ValidarLongitudContrasenia implements Validacion {
    public boolean esValida(String usuario, String contrasenia) throws ExceptionLongitudContrasenia {
        boolean valida = contrasenia.length() >= 8;
        if(valida)
            return true;
        else
            throw new ExceptionLongitudContrasenia();
    }
}
