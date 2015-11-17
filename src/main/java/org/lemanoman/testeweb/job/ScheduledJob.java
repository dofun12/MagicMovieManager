package org.lemanoman.testeweb.job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
 
public class ScheduledJob extends QuartzJobBean{
    private ControladorQuartz quartz;
    
    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
	quartz.metodoQuartz();
	System.out.println("TEste");
    }
    
}