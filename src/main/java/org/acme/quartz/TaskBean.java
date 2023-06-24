package org.acme.quartz;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import io.quarkus.scheduler.Scheduled;

@ApplicationScoped
public class TaskBean {

    @Transactional
    @Scheduled(every = "10s", identity = "task-job")
//    @WithSpan("schedule") //no trace/span created without that
    void schedule() {
        Task task = new Task();
        task.persist();
        Log.info("created task");
    }
}
