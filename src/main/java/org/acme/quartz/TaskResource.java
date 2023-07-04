package org.acme.quartz;

import java.util.List;

import io.quarkus.logging.Log;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/tasks")
public class TaskResource {

    @GET
    public List<Task> listAll() {
        return Task.listAll();
    }

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        Log.info("hello");
        return "hello";
    }

    @GET
    @Path("/trigger")
    @Produces(MediaType.TEXT_PLAIN)
    public String trigger() throws Exception {
        // Create a new instance of the SchedulerFactory
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();

        // Retrieve a scheduler from the factory
        Scheduler scheduler = schedulerFactory.getScheduler();

        Log.info("going to start scheduler");

        // Start the scheduler
        scheduler.start();

        // Define the job
        JobDetail job = JobBuilder.newJob(TaskCreateJob.class)
                .withIdentity("myJob", "group1")
                .build();

        // Define the trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever())
                .build();

        // Associate the job and trigger with the scheduler
        scheduler.scheduleJob(job, trigger);

        // Sleep for a while to allow the job to run
        Thread.sleep(3000);

        // Shutdown the scheduler
        scheduler.shutdown();
        return "hello";
    }
}
