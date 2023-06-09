package org.acme;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class LoggingInterceptorTest1 {

    //FIXME fails with: Invalid use of io.quarkus.test.junit.mockito.InjectSpy - could not resolve the bean of type: org.acme.LoggingInterceptor. Offending field is loggingInterceptor of test class class org.acme.LoggingInterceptorTest1_Subclass
    @InjectSpy
    LoggingInterceptor loggingInterceptor;

    @Test
    public void testAroundInvoke() {
        serviceMethodWithInterceptor();

        ArgumentCaptor<String> logMessageCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(loggingInterceptor).log(logMessageCaptor.capture());

        assertEquals("inside LoggingInterceptor @AroundInvoke", logMessageCaptor.getValue());
    }

    @Logging
    public void serviceMethodWithInterceptor() {
        Log.info("in service method serviceMethodWithInterceptor");
    }


}