package org.acme;

import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import org.acme.model.JobType;
import org.acme.service.Service;
import org.acme.service.ServiceFactory;

import javax.enterprise.event.Observes;
import javax.management.InstanceNotFoundException;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

@Path("/test")
public class TestResource {

    private static final Logger LOG = Logger.getLogger("TestResource");

    void onStart(@Observes StartupEvent ev) throws InstanceNotFoundException {
        Service serviceA = ServiceFactory.getFor(JobType.A);
        LOG.info(serviceA.hello());

        Service serviceC = ServiceFactory.getFor(JobType.C);
    }




















// TODO test context propagation, precondition:
//    https://quarkus.io/guides/transaction
//    If your @Transactional-annotated method returns a reactive value, such as:
//    - CompletionStage (from the JDK)
//    - Publisher (from Reactive-Streams)
//    - Any type which can be converted to one of the two previous types using Reactive Type Converters
//    then the behaviour is a bit different, because the transaction will not be terminated until the returned reactive value is terminated.
//    In effect, the returned reactive value will be listened to and if it terminates exceptionally the transaction will be marked for rollback,
//    and will be committed or rolled-back only at termination of the reactive value.

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public String transactionalWithMutiny() {
        LOG.info("short task started");

        Uni.createFrom().item("testitem")
                .onItem()
                .invoke(i -> LOG.info("long task started"))
                .emitOn(Infrastructure.getDefaultExecutor())
                .invoke(i -> {
                    try {
                        Thread.sleep(3000);
                        LOG.info("long task finished");
                    } catch (InterruptedException e) {

                    }
                })
                .log()
                .subscribe().with(i -> {});

        LOG.info("short task finished");
        return "";
    }

}