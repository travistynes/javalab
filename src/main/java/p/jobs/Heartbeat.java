package p.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;

@DisallowConcurrentExecution
public class Heartbeat implements Job {
    private static Logger log = Logger.getLogger(Heartbeat.class);
    
    /*
    Quartz job interface method. Called when job is invoked.
    */
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        log.info("Heartbeat.");
    }
}