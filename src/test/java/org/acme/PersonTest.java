package org.acme;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PersonTest {

    @Test @Testdata //default to testname + .sql -> testHelloEndpoint.sql
    public void testHelloEndpoint() {
        Log.info("test started");
    }

    @Test @Testdata("person.sql")
    public void testHelloEndpointCustom() {
        Log.info("test started");
    }



}