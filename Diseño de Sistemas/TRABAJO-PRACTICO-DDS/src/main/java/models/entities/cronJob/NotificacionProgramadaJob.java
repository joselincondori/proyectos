package models.entities.cronJob;

import lombok.Getter;
import models.entities.domain.notificaciones.tiempo.NotificacionProgramada;
import models.repositories.NotificacionProgramadaRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.List;

public class NotificacionProgramadaJob implements Job {
    private NotificacionProgramadaRepository notificacionProgramadaRepository;

    public static int SEGUNDOS = 3600;

    public NotificacionProgramadaJob() {
        this.notificacionProgramadaRepository = new NotificacionProgramadaRepository();
    }

    public void execute(JobExecutionContext jExeCtx) throws JobExecutionException {//handle JobExecutionException
        System.out.println("Cron Job Notificacion Programada is running ...");
        List<NotificacionProgramada> notificacionProgramadas = (List<NotificacionProgramada>) notificacionProgramadaRepository.findAll();
        if(notificacionProgramadas == null || notificacionProgramadas.isEmpty())
            return;
        notificacionProgramadas.forEach(n -> n.notificar());
        notificacionProgramadas.forEach(n -> notificacionProgramadaRepository.update(n));
    }
}