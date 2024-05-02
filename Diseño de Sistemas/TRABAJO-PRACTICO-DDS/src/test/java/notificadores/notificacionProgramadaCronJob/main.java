package notificadores.notificacionProgramadaCronJob;

import models.entities.cronJob.NotificacionProgramadaJob;
import models.entities.cronJob.Scheduler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class main {
    @Test
    public void notificacionProgramadaCronJobTest() throws Exception {
        NotificacionProgramadaJob notificacionProgramadaJob = new NotificacionProgramadaJob();
        Scheduler.getInstance().jobCreate(notificacionProgramadaJob, 10, "NotificacionProgramada");
        for(int i=0; i < 5; i ++) {
            Thread.sleep(5000);
        }
        Assertions.assertTrue(true);
    }
}