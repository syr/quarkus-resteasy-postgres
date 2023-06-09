package org.acme;

import javax.enterprise.context.ApplicationScoped;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import io.quarkus.arc.Priority;
import io.quarkus.logging.Log;

@Logging
@Priority(10)
@Interceptor
@ApplicationScoped
public class LoggingInterceptor {

    @AroundInvoke
    Object log(InvocationContext ctx) throws Exception {
        log("inside LoggingInterceptor @AroundInvoke");
        return ctx.proceed();
    }

    public void log(String message) {
        Log.info(message);
    }
}