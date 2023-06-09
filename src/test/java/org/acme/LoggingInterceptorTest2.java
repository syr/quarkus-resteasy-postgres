package org.acme;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class LoggingInterceptorTest2 {

    @Test
    public void testAroundInvoke() {
        LoggingInterceptor loggingInterceptor = Mockito.spy(LoggingInterceptor.class);

        serviceMethodWithInterceptor();

        ArgumentCaptor<String> logMessageCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(loggingInterceptor).log(logMessageCaptor.capture());

        //FIXME fails with: Wanted but not invoked: loggingInterceptor.log(<Capturing argument>);
        assertEquals("inside LoggingInterceptor @AroundInvoke", logMessageCaptor.getValue());
    }

    @Logging
    public void serviceMethodWithInterceptor() {
        Log.info("in service method serviceMethodWithInterceptor");
    }


}