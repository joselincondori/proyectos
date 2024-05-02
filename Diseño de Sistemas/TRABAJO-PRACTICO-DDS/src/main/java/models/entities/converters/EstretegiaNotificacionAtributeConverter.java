package models.entities.converters;

import models.entities.domain.notificaciones.estrategia.Email.EstrategiaEmail;
import models.entities.domain.notificaciones.estrategia.EstrategiaNotificacion;
import models.entities.domain.notificaciones.estrategia.Whatsapp.EstrategiaWpp;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
public class EstretegiaNotificacionAtributeConverter implements AttributeConverter<EstrategiaNotificacion, String> {

    @Override
    public String convertToDatabaseColumn(EstrategiaNotificacion estrategiaNotificacion) {
        String nombreEstrategia = null;
        String className = estrategiaNotificacion.getClass().getSimpleName();
        switch (className){
            case "EstrategiaEmail": nombreEstrategia = "email"; break;
            case "EstrategiaWpp": nombreEstrategia = "wpp"; break;
        }
        return nombreEstrategia;
    }

    @Override
    public EstrategiaNotificacion convertToEntityAttribute(String s) {
        EstrategiaNotificacion estrategia = null;
        if(Objects.equals(s, "wpp"))
            estrategia = new EstrategiaWpp();
        if(Objects.equals(s, "email"))
            estrategia = new EstrategiaEmail();
        return estrategia;
    }
}
