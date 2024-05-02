package models.entities.cronJob;

import org.apache.poi.ss.formula.functions.T;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class Scheduler {
    private static final String NAME_OF_GROUP = "CronJob";
    private static final String NAME_OF_TRIGGER = "triggerStart";
    private static org.quartz.Scheduler scheduler;
    private static Scheduler instance = null;

    public static Scheduler getInstance() {
        if(instance == null) {
            instance = new Scheduler();
        }
        return instance;
    }

    private void startScheduler() throws SchedulerException {
        if(scheduler == null) {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
        }
    }

    public void jobCreate(Job job, String cronExpresion, String nombreJob) throws Exception {
        startScheduler();
        System.out.println(" The name of the QuartzScheduler main thread is: " + Thread.currentThread().getName());
        Trigger triggerNew =  createCronTrigger(cronExpresion);
        JobDetail jobInstance = JobBuilder.newJob(job.getClass()).withIdentity(nombreJob, NAME_OF_GROUP).build();
        scheduleJob(jobInstance, triggerNew);
    }

    public void jobCreate(Job job, int seconds, String nombreJob) throws Exception {
        startScheduler();
        // mostrar thread que se esta ejecutando
        System.out.println(" The name of the QuartzScheduler main thread is: " + Thread.currentThread().getName());
        Trigger triggerNew =  createTrigger(seconds);
        JobDetail jobInstance = JobBuilder.newJob(job.getClass()).withIdentity(nombreJob, NAME_OF_GROUP).build();
        scheduleJob(jobInstance, triggerNew);
    }

    public void crearCronJobs() throws Exception {
        // Notificacion Programada
        NotificacionProgramadaJob notificacionProgramadaJob = new NotificacionProgramadaJob();
        jobCreate(notificacionProgramadaJob, NotificacionProgramadaJob.SEGUNDOS, "NotificacionProgramada");

        // Actualizar Confianza Personas y Comunidades
        ActualizarConfianzasJob actualizarConfianzasJob = new ActualizarConfianzasJob();
        jobCreate(actualizarConfianzasJob, ActualizarConfianzasJob.CRON_EXPRESION, "ActualizarConfianzas");

        // Generar Rankings
        GenerarRankingsJob generarRankingsJob = new GenerarRankingsJob();
        jobCreate(generarRankingsJob, GenerarRankingsJob.CRON_EXPRESION, "GenerarRankings");
    }

    private void scheduleJob(JobDetail jobInstance, Trigger triggerNew) throws Exception {
        scheduler.scheduleJob(jobInstance, triggerNew);
    }

    // trigger con cron expresion
    private Trigger createCronTrigger(String cronExpresion) {
        Trigger triggerNew = TriggerBuilder.newTrigger().withIdentity(NAME_OF_TRIGGER, NAME_OF_GROUP)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpresion)).build();
        return triggerNew;
    }


    private Trigger createTrigger(int seconds) {
        Trigger triggerNew = TriggerBuilder.newTrigger().withIdentity(NAME_OF_TRIGGER, NAME_OF_GROUP)
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(seconds).repeatForever())
                .build();
        return triggerNew;
    }
}
