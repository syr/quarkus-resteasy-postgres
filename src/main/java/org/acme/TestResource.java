package org.acme;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import org.acme.service.Service;

import javax.management.InstanceNotFoundException;
import java.util.logging.Logger;

@Path("/test")
@ApplicationScoped
public class TestResource {

    private static final Logger LOG = Logger.getLogger("TestResource");

    @Inject
    @ServiceA
    Service serviceA;

    @Inject
    @ServiceB
    Service serviceB;


    void onStart(@Observes StartupEvent ev) throws InstanceNotFoundException {
        String s="";
//        Service serviceA = ServiceFactory.getFor(JobType.A);
//        LOG.info(serviceA.hello());
//
//        //ServiceFactory not really required
//        Service serviceA2 = CDI.getInstance(Service.class, JobType.A);
//
//
//        Service serviceC = ServiceFactory.getFor(JobType.C); //fail test
    }
}