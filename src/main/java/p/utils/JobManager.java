package p.utils;

import javax.servlet.http.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import org.apache.log4j.Logger;
import static org.quartz.CronScheduleBuilder.cronSchedule;

@WebServlet (
    loadOnStartup=2,
    urlPatterns = {"/jobs"} // Tomcat doesn't seem to init the servlet without urlPatterns set...?
)
public class JobManager extends HttpServlet {
    private static Logger log = Logger.getLogger(JobManager.class);
    private static Scheduler scheduler;
    
    /*
    Called on servlet initialization (on deployment).
    */
    public void init(ServletConfig config) throws ServletException {
        try {
            log.info("Job manager initializing.");
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            
            // Start the quartz scheduler.
            scheduler.start();
            
            // Schedule jobs.
            this.scheduleJobs();
            
            log.info("Quartz scheduler started.");
            
            super.init(config);
        } catch(SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
    
    /*
    Called when servlet is destroyed (on undeployment).
    */
    public void destroy() throws RuntimeException {
        try {
            log.info("Job manager shutting down.");
            
            // Shutdown the quartz scheduler.
            if(!scheduler.isShutdown()) {
                scheduler.shutdown();
                log.info("Quartz scheduler stopped.");
            }
        } catch(SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
    
    /*
    Adds jobs to the scheduler.
    */
    private void scheduleJobs() throws SchedulerException {
        this.scheduleHeartbeat();
    }
    
    /*
    Schedules the heartbeat job. This is an example job.
    */
    private void scheduleHeartbeat() throws SchedulerException {
        // Cron expressions: http://quartz-scheduler.org/documentation/quartz-2.x/tutorials/tutorial-lesson-06
        String schedule = "0 0/1 * * * ?"; // Every minute at 0 seconds after the minute.
        
        JobDetail job = newJob(p.jobs.Heartbeat.class).withIdentity("HeartbeatJob", "Group1").build();
        
        Trigger trigger = newTrigger()
            .withIdentity("HeartbeatTrigger", "Group1")
            //.startNow()
            .withSchedule(cronSchedule(schedule))
            .build();
        
        scheduler.scheduleJob(job, trigger);
        
        log.info("Heartbeat schedule: " + schedule);
    }
}
