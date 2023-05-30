package org.acme;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PersonTest {

    @Test
    @Testdata("testdata/person.sql")
    public void testHelloEndpoint() {
        Log.info("test started");
    }



}