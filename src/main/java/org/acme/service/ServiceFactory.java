package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.acme.ServiceA;
import org.acme.ServiceB;

@ApplicationScoped
public class ServiceFactory {
    @Produces
    @ServiceA
    Service produceServiceA() {
         return new org.acme.service.Service("A");
    }

    @Produces
    @ServiceB
    Service produceServiceB() {
        return new org.acme.service.Service("B");
    }
}
