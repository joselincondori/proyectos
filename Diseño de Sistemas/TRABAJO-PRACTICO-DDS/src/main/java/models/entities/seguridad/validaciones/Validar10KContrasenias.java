package models.entities.seguridad.validaciones;

import lombok.Getter;
import models.entities.seguridad.excepciones.ExcepcionComun;
import models.entities.seguridad.excepciones.ExcepcionLecturaArchivo;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Validar10KContrasenias implements Validacion {
    private String pathArchivoContrasenias = "src/main/resources/10mil-mas-comunes.txt";
    @Getter
    private List<String> contraseniasComunes;

    public boolean esValida(String usuario, String contrasenia) throws ExcepcionComun {
        if(contraseniasComunes == null) {
            Path path = Paths.get(pathArchivoContrasenias);
            try {
                Stream<String> contras = Files.lines(path);
                contraseniasComunes = contras.collect(Collectors.toList());
                contras.close();
            } catch (IOException error) {
                throw new ExcepcionLecturaArchivo();
            }
        }
        boolean valida = contraseniasComunes.stream().noneMatch(contraseniaComun -> contraseniaComun.equals(contrasenia));
        if(valida)
            return true;
        else
            throw new ExcepcionComun();
    }
}
