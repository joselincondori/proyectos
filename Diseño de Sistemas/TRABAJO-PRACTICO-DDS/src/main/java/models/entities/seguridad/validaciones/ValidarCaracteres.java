package models.entities.seguridad.validaciones;

import models.entities.seguridad.excepciones.ExcepcionCaracteresInvalidos;

public class ValidarCaracteres implements Validacion {
    public boolean esValida(String usuario, String contrasenia) throws ExcepcionCaracteresInvalidos {
        if(contrasenia.length() == 0)
            throw new ExcepcionCaracteresInvalidos();
        for(int i = 0; i < contrasenia.length(); i++) {
            if(caracterNoEsValido(contrasenia, i)) {
                throw new ExcepcionCaracteresInvalidos();
            }
        }
        return true;
    }
    private boolean caracterNoEsValido(String contrasenia, int i){
        int code = contrasenia.codePointAt(i);
        return !(this.validarCaracterASCII(code) || this.validarCaracterUNICODE(code));
    }
    private boolean validarCaracterASCII(int caracter){
        return (caracter >= 32 && caracter <= 126) || (caracter >= 128 && caracter <= 255);
    }
    private boolean validarCaracterUNICODE(int caracter){
        return caracter >= 160 && caracter <= 9835;
    }
}
