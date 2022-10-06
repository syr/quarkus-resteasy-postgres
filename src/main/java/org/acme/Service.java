package org.acme;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Service  {

    @WithSpan("servicemethod")
    public void hello() {
        Log.info("hello from Service");
    }
}