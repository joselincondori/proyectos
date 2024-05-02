package models.entities.domain.InformesRankings;

import config.Config;
import models.entities.domain.rankings.EntradaRanking;
import models.entities.domain.persona.Persona;
import models.entities.exportadores.Exportable;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Informe implements Exportable {
    private String descripcion;
    private Persona destinatario;
    private List<EntradaRanking> entradas;
    private Map<String, List<String>> datos;

    public Informe(String descrip, Persona destinat) {
        this.descripcion = descrip;
        this.destinatario = destinat;
        this.entradas = new ArrayList<>();
    }

    @Override
    public Map<String, List<String>> getDatos() {
        return this.getDatosEntidades();
    }

    public Map<String, List<String>> getDatosEntidades(){
        Map<String, List<String>> datos = new HashMap<>();
        agregarDato(datos,Config.CLAVE_DESCRIP,descripcion);
        agregarDato(datos,Config.CLAVE_CAMPOS,"Nombre", "Tipo","Provincia","Municipio","Localidad", "Posicion", "Puntaje");
        int i = 0;
        for(EntradaRanking entrada : entradas){
            String nombre = entrada.getEntidad().getNombre();
            String tipo = entrada.getEntidad().getTipo().getNombre();
            String provincia = entrada.getEntidad().getProvincia().getNombre();
            String municipio = entrada.getEntidad().getMunicipio().getNombre();
            String localidad = entrada.getEntidad().getLocalidad().getNombre();
            String posicion = String.valueOf(entrada.getPosicion());
            String puntaje = String.valueOf(entrada.getPuntaje());

            agregarDato(datos, String.valueOf(i),nombre,tipo,provincia,municipio,localidad,posicion,puntaje);
            i++;
        }
        return datos;
    }

    public void agregarDato(Map<String, List<String>> datos,String clave, String ... valor){
        Collections.addAll(datos.get(clave), valor);
    }
}
