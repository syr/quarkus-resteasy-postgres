package org.acme;

import io.quarkus.logging.Log;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.entity.FilePart;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class DownloadSyncResource {

    @ConfigProperty(name = "quarkus.datasource.jdbc.url")
    String jdbc;

    @Inject
    SessionFactory sessionFactory;

    void onStart(@Observes StartupEvent ev) {
//        Log.info("first query");
//        FilePart f = FilePart.find("id=1").firstResult();
//        Statistics stats = sessionFactory.getStatistics();
//        Log.info("QueryCacheHitCount: " + stats.getQueryCacheHitCount());
//        Log.info("QueryExecutionCount: " + stats.getQueryExecutionCount());
//
//        Log.info("second query");
//        FilePart f2 = FilePart.find("id=1").firstResult();
//        stats = sessionFactory.getStatistics();
//        Log.info("QueryCacheHitCount: " + stats.getQueryCacheHitCount());
//        Log.info("QueryExecutionCount: " + stats.getQueryExecutionCount());
    }
}