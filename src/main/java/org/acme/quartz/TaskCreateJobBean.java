package org.acme.quartz;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.arc.Unremovable;
import io.quarkus.logging.Log;
import org.quartz.JobExecutionContext;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Unremovable
public class TaskCreateJobBean  {
    @WithSpan
    public void execute() {
        Log.info("created task");
    }
}
