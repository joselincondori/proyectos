package models.entities.domain.InformesRankings;

import models.entities.domain.rankings.RankingGenerado;
import models.entities.exportadores.Exportador;

import java.util.ArrayList;
import java.util.List;

public class GeneradorInformes {
    private static GeneradorInformes instance;

    public static GeneradorInformes getInstance() {
        if(instance == null) {
            instance = new GeneradorInformes();
        }
        return instance;
    }
    public List<Informe> generar(RankingGenerado ranking) {
        List<Informe> informes = new ArrayList<>();
        for(int i=0; i < ranking.getRanking().size(); i++) {
            // TODO  (obtencion de usuario destinatario, sacar el null de la linea de abajo y reemplazar)
            Informe informe = new Informe(ranking.getDescripcion(), null);
            informe.getEntradas().add(ranking.getRanking().get(i));
            informes.add(informe);
        }
        informes = agruparEntradas(informes);
        return informes;
    }

    public List<Informe> agruparEntradas(List<Informe> informes) {
        for(int i=0; i < informes.size(); i++) {
            for(int j=i+1; j < informes.size(); j++ ){
                // Si coincide el destinatario se agrupan las entradas
                if(informes.get(i).getDestinatario().equals(informes.get(j).getDestinatario())) {
                    informes.get(i).getEntradas().addAll(informes.get(j).getEntradas());
                    informes.remove(j);
                }
            }
        }
        return informes;
    }

    public void exportar(Informe informe) {
        Exportador.getInstancia().exportar(informe);
    }
}
