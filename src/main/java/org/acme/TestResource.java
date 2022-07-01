package org.acme;

import io.quarkus.arc.All;
import io.quarkus.runtime.StartupEvent;
import org.acme.service.Service;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

@Path("/test")
public class TestResource {

    private static final Logger LOG = Logger.getLogger("DownloadSyncResource");

    @Inject @All
    Instance<Service> service;

    void onStart(@Observes StartupEvent ev) {
        Service service = this.service.get();
        LOG.info("test: ");
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sync() {

        return "";
    }

}