package servicios.georef;

import models.entities.domain.georef.ListadoDeMunicipios;
import models.entities.domain.georef.ListadoDeProvincias;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ServicioGeoref {
    private static ServicioGeoref instancia = null; // singleton
    private Retrofit retrofit;
    private static int maximaCantidadRegistrosDefault = 200;
    private static final  String urlAPI = "https://apis.datos.gob.ar/georef/api/";
    //constructor
    private ServicioGeoref(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServicioGeoref getInstance(){
        if(instancia == null){
            instancia = new ServicioGeoref();
        }
        return instancia;
    }

    public ListadoDeProvincias listadoDeProvincias() throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class); //creo una clase q implemente la interfaz

        Call<ListadoDeProvincias> requestProvinciasArg = georefService.provincias();
        Response<ListadoDeProvincias> responseProvinciasArg =requestProvinciasArg.execute(); //recien aca hace la request
        return responseProvinciasArg.body(); //el body devuelve el listado
    }

    public ListadoDeMunicipios listadoDeMunicipios(int idProvincia) throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoDeMunicipios> requestMunicipiosDeProvincia = georefService.municipios(idProvincia);
        Response<ListadoDeMunicipios> responseMunicipiosDeProvincia = requestMunicipiosDeProvincia.execute();

        return responseMunicipiosDeProvincia.body();
    }
}
