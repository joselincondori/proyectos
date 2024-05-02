package models.entities.seguridad.validaciones;

import models.entities.seguridad.excepciones.ExcepcionContraseniaContieneUsuario;

public class ValidarContraseniaNoContieneUsuario implements Validacion {
    public boolean esValida(String usuario, String contrasenia) throws ExcepcionContraseniaContieneUsuario {
        String usuarioLowerCase = usuario.toLowerCase();
        String contraseniaLowerCase = contrasenia.toLowerCase();

        boolean valida = !contraseniaLowerCase.contains(usuarioLowerCase);

        if(valida)
            return true;
        else
            throw new ExcepcionContraseniaContieneUsuario();
    }
}
