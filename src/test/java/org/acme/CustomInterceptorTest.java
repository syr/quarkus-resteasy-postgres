package org.acme;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class CustomInterceptorTest {

    @Test
    public void testAroundInvoke() {
        serviceMethod_CallingAroundInvoke();
        Log.info("############");
        serviceMethod_NotCallingAroundInvoke(); //WHY?
    }

    /*
    Test with samples from
    https://github.com/quarkusio/quarkus/blob/2.16/independent-projects/arc/tests/src/test/java/io/quarkus/arc/test/interceptors/Logging.java
    https://github.com/quarkusio/quarkus/blob/2.16/independent-projects/arc/tests/src/test/java/io/quarkus/arc/test/interceptors/LoggingInterceptor.java
     */
    @Test
    public void testAroundInvoke2() {
        serviceMethod_CallingAroundInvoke2();
        Log.info("############");
        serviceMethod_NotCallingAroundInvoke2(); //WHY?
    }

    @MyAnnotation(value = false)
    public void serviceMethod_CallingAroundInvoke() {
        Log.info("in service method serviceMethod_CallingAroundInvoke");
    }

    @MyAnnotation(value = true)
    public void serviceMethod_NotCallingAroundInvoke() {
        Log.info("in service method serviceMethod_NotCallingAroundInvoke");
    }

    @Logging(value = false)
    public void serviceMethod_CallingAroundInvoke2() {
        Log.info("in service method serviceMethod_CallingAroundInvoke");
    }

    @Logging(value = true)
    public void serviceMethod_NotCallingAroundInvoke2() {
        Log.info("in service method serviceMethod_NotCallingAroundInvoke");
    }

}