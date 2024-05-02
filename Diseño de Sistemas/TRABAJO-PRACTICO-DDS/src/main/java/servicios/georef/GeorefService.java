package servicios.georef;

import models.entities.domain.georef.ListadoDeMunicipios;
import models.entities.domain.georef.ListadoDeProvincias;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeorefService {
    //posibles llamadas con rutas relativas
    @GET("provincias")
    Call<ListadoDeProvincias> provincias();

    @GET("provincias")
    Call<ListadoDeProvincias> provincias(@Query("campos") String campos); // el query le indica que tiene q tirar un ? para traer esos datos(id,nombre)

    @GET("municipios")
    Call<ListadoDeMunicipios> municipios(@Query("provincia") int idProvincia);

    @GET("municipios")
    Call<ListadoDeMunicipios> municipios(@Query("provincia") int idProvincia,@Query("campos") String campos);
}
