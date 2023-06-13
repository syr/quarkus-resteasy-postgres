package org.acme.amqp.producer;

import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.annotations.Blocking;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/quotes")
public class QuotesResource {

    @Channel("quote-requests") Emitter<String> quoteRequestEmitter;

    @Incoming("quotes")
    @Blocking
    public void process(io.vertx.core.json.JsonObject quoteRequest) throws InterruptedException {
        Log.info("quoteRequest processed");
    }

    /**
     * Endpoint to generate a new quote request id and send it to "quote-requests" RabbitMQ exchange using the emitter.
     *
     * Test by:
     * GET localhost:8080/quotes/
     *
     * Workflow:
     * GET http://localhost:8080/quotes/request
     * -> producer:  msg to OUTPUT  channel "quote-requests"
     * -> processor: msg from INPUT channel "requests"       queue "quote-requests"
     * -> processor: msg = new Quote()
     * -> processor: msg to OUTPUT  channel "quotes"
     * -> producer:  msg from INPUT channel "quotes"         queue "quotes"
     *
     */
    @GET
    @Path("/request")
    @Produces(MediaType.TEXT_PLAIN)
    public String createRequest() {
        UUID uuid = UUID.randomUUID();
        quoteRequestEmitter.send(uuid.toString());
        return uuid.toString();
    }
}
