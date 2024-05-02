package models.entities.cronJob;

import models.entities.domain.rankings.Rankeador;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class GenerarRankingsJob implements Job {
    public static String CRON_EXPRESION;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Cron Job Generar Rankings is running ...");
        Rankeador.getInstance().generarRankings();
    }
}
