package org.acme.quartz;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.logging.Log;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class TaskCreateJob implements Job {
    @Override
    @WithSpan
    public void execute(JobExecutionContext jobExecutionContext) {
        Log.info("created task");
    }
}
