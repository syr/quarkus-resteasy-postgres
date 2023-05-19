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
        serviceMethod_NotCallingAroundInvoke();
    }

    @MyAnnotation(value = false)
    public void serviceMethod_CallingAroundInvoke() {
        Log.info("in service method serviceMethod_CallingAroundInvoke");
    }

    @MyAnnotation(value = true)
    public void serviceMethod_NotCallingAroundInvoke() {
        Log.info("in service method serviceMethod_NotCallingAroundInvoke");
    }

}