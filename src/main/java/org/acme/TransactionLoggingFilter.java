package org.acme;

import io.vertx.core.http.HttpServerRequest;
import org.jboss.logging.MDC;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class TransactionLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Context
    HttpServerRequest request;

    @Override
    public void filter(ContainerRequestContext context) {
        MDC.put("transactionId", request.getHeader("transactionId"));
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        MDC.remove("transactionId");
    }
}