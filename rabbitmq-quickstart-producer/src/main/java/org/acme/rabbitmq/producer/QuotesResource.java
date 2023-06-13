package org.acme.rabbitmq.producer;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.annotations.Blocking;
import org.acme.rabbitmq.model.Quote;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@Path("/quotes")
public class QuotesResource {

    @Channel("quote-requests") Emitter<String> quoteRequestEmitter;

    @Channel("quotes") Multi<Quote> quotes;

    /**
     * Endpoint retrieving the "quotes" queue and sending the items to a server sent event.
     */
    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<Quote> stream() {
        return quotes;
    }


    /**
     * Endpoint to generate a new quote request id and send it to "quote-requests" RabbitMQ exchange using the emitter.
     *
     * Test by:
     * GET localhost:8080/quotes/
     * GET http://localhost:8080/quotes/request
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
