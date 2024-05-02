package config;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

public class Config {
    private static final Properties prop = new Properties();
    private static Config instancia;
    public static final String RUTA_EXPORTACION = "tmp/";
    public static final String CLAVE_DESCRIP = "Clave_Descrip";
    public static final String CLAVE_CAMPOS = "Clave_Campos";


    private Config() { //porque es un Singleton
    }

    public static Config obtenerInstancia() { // Singleton
        if (instancia == null){
            instancia = new Config();
        }
        return instancia;
    }

    public String obtenerDelConfig(String key) {
        try (InputStream input = Config.class.getResourceAsStream("/config.properties")) {
            prop.load(input);
            return prop.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}