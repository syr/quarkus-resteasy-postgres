package org.acme;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.entity.FilePart;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class DownloadSyncResourceTest {
    @Inject
    SessionFactory sessionFactory;
    @Test
    public void testHelloEndpoint() {
        Log.info("first query");
        FilePart f1 = FilePart.find("id=1").firstResult();
        Statistics stats = sessionFactory.getStatistics();
        Log.info("QueryCacheHitCount: " + stats.getQueryCacheHitCount());
        Log.info("QueryExecutionCount: " + stats.getQueryExecutionCount());
        assertEquals(0, stats.getQueryCacheHitCount());
        assertEquals(1, stats.getQueryExecutionCount());

        Log.info("second query");
        FilePart f2 = FilePart.find("id=1").firstResult();
        assertSame(f2, f1); //identical object from first query is returned

        stats = sessionFactory.getStatistics();
        Log.info("QueryCacheHitCount: " + stats.getQueryCacheHitCount());
        Log.info("QueryExecutionCount: " + stats.getQueryExecutionCount());
        assertEquals(1, stats.getQueryCacheHitCount()); //fails, there should be a cache hit for identical query
        assertEquals(1, stats.getQueryExecutionCount());
    }

}