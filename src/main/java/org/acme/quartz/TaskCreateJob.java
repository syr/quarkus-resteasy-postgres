package org.acme.quartz;

import io.quarkus.arc.Arc;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class TaskCreateJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        Arc.container().select(TaskCreateJobBean.class).get().execute();
    }
}
