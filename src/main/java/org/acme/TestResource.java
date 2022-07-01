package org.acme;

import io.quarkus.arc.All;
import io.quarkus.arc.InstanceHandle;
import io.quarkus.runtime.StartupEvent;
import org.acme.model.JobType;
import org.acme.service.Service;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Logger;

@Path("/test")
public class TestResource {

    private static final Logger LOG = Logger.getLogger("DownloadSyncResource");

    @Inject @All
    List<InstanceHandle<Service>> services;

    void onStart(@Observes StartupEvent ev) {
        JobType jobTypeFromAnnotation = ((SupportsJobType) services.get(0).getBean().getQualifiers().stream().filter(q -> q.annotationType() == SupportsJobType.class).findFirst().get()).value();
        //TODO in service method: determine which service to use by poassed JobEntity
        LOG.info("test: ");
//        Arc.container().listAll(Service.class, SupportsJobType.class);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sync() {

        return "";
    }

}