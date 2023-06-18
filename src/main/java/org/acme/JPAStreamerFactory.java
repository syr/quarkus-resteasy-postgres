package org.acme;

import com.speedment.jpastreamer.application.JPAStreamer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class JPAStreamerFactory {

    @PersistenceContext
    EntityManager entityManager;

    @Produces
    JPAStreamer buildJPAStreamer() {
        return JPAStreamer.of(() -> entityManager);
    }
}
