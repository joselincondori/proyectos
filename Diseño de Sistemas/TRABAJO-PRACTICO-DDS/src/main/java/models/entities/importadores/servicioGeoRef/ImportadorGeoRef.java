package models.entities.importadores.servicioGeoRef;
import models.entities.domain.georef.ListadoDeMunicipios;
import models.entities.domain.georef.ListadoDeProvincias;
import models.entities.domain.georef.Municipio;
import models.entities.domain.georef.Provincia;
import models.entities.importadores.Importador;
import models.entities.domain.georef.ListadoDeLocalidades;
import models.entities.domain.georef.Localidad;
import models.repositories.LocalidadRepository;
import models.repositories.MunicipioRepository;
import models.repositories.ProvinciaRepository;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImportadorGeoRef implements Importador {
    private final ProvinciaRepository provinciaRepository = new ProvinciaRepository();
    private final MunicipioRepository municipioRepository = new MunicipioRepository();
    private final LocalidadRepository localidadRepository = new LocalidadRepository();

    private static ImportadorGeoRef instancia = null;
    private Retrofit retrofit;
    private static final  String urlAPI = "https://apis.datos.gob.ar/georef/api/";
    //constructor
    private ImportadorGeoRef(){
        this.retrofit = new Retrofit.Builder()
                                    .baseUrl(urlAPI)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
    }

    public static ImportadorGeoRef getInstance(){
        if(instancia == null){
            instancia = new ImportadorGeoRef();
        }
        return instancia;
    }

    public List<ProvinciaGeo> getProvinciasGeo() throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoDeProvincias> requestProvinciasArg = georefService.provincias("id,nombre");
        Response<ListadoDeProvincias> responseProvinciasArg =requestProvinciasArg.execute();
        System.out.println("entro a provincias");

        List<ProvinciaDto> provinciaDtos = responseProvinciasArg.body().getProvincias();
        List<ProvinciaGeo> provinciasGeo = new ArrayList<>();
        for (ProvinciaDto provinciaDto : provinciaDtos) {
            Provincia provincia = new Provincia(provinciaDto.getNombre());
            ProvinciaGeo provinciaGeo = new ProvinciaGeo(provinciaDto.getId(), provincia);
            provinciasGeo.add(provinciaGeo);
        }
        return provinciasGeo;
    }

    public List<MunicipioGeo> getMunicipiosGeo(List<ProvinciaGeo> provinciasGeo) throws IOException {
        List<MunicipioGeo> municipiosGeo = new ArrayList<>();
        for (ProvinciaGeo provinciaGeo : provinciasGeo) {
            Long idProvincia = provinciaGeo.getIdGeo();
            GeorefService georefService = this.retrofit.create(GeorefService.class);
            Call<ListadoDeMunicipios> requestMunicipiosDeProvincia = georefService.municipios(idProvincia,"id,nombre",5000);
            Response<ListadoDeMunicipios> responseMunicipiosDeProvincia = requestMunicipiosDeProvincia.execute();
            System.out.println("entro a municipios");
            List<MunicipioDto> municipioDtos = responseMunicipiosDeProvincia.body().getMunicipios();

            for(MunicipioDto municipioDto : municipioDtos) {
                Municipio municipio = new Municipio(municipioDto.getNombre(), provinciaGeo.getProvincia());
                MunicipioGeo municipioGeo = new MunicipioGeo(municipioDto.getId(), municipio);
                municipiosGeo.add(municipioGeo);
            }
        }
        return municipiosGeo;
    }

    public List<Localidad> getLocalidades(List<MunicipioGeo> municipiosGeo) throws IOException {
        List<Localidad> localidades = new ArrayList<>();
        for (MunicipioGeo municipioGeo : municipiosGeo) {
            Long idMunicipio = municipioGeo.getIdGeo();
            GeorefService georefService = this.retrofit.create(GeorefService.class);
            Call<ListadoDeLocalidades> requestLocalidadesDeMunicipio = georefService.localidades(idMunicipio,"id,nombre",5000);
            Response<ListadoDeLocalidades> responseLocalidadesDeMunicipio = requestLocalidadesDeMunicipio.execute();
            System.out.println("entro a localidades");
            List<LocalidadDto> localidadesDto = responseLocalidadesDeMunicipio.body().getLocalidades();

            for(LocalidadDto localidadDto : localidadesDto) {
                Localidad localidad = new Localidad(localidadDto.getNombre(), municipioGeo.getMunicipio());
                localidades.add(localidad);
            }
        }
        return localidades;
    }

    @Override
    public void importar(){
        try {
            List<ProvinciaGeo> provinciasGeo = getProvinciasGeo();
            List<MunicipioGeo> municipiosGeo = getMunicipiosGeo(provinciasGeo);
            List<Localidad> localidades = getLocalidades(municipiosGeo);

            List<Provincia> provincias = provinciasGeo.stream().map(ProvinciaGeo::getProvincia).toList();
            List<Municipio> municipios = municipiosGeo.stream().map(MunicipioGeo::getMunicipio).toList();

            this.provinciaRepository.saveAll(provincias);
            this.municipioRepository.saveAll(municipios);
            this.localidadRepository.saveAll(localidades);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

}