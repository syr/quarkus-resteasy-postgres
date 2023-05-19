package org.acme;

import java.util.concurrent.atomic.AtomicReference;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import io.quarkus.arc.Priority;
import io.quarkus.logging.Log;

/*
https://github.com/quarkusio/quarkus/blob/2.16/independent-projects/arc/tests/src/test/java/io/quarkus/arc/test/interceptors/LoggingInterceptor.java
 */
@Logging
@Priority(10)
@Interceptor
public class LoggingInterceptor {

    static final AtomicReference<Object> LOG = new AtomicReference<Object>();

    @AroundInvoke
    Object log(InvocationContext ctx) throws Exception {
        boolean flag = ctx.getMethod().getAnnotation(Logging.class).value();
        Log.info("inside MyInterceptor @AroundInvoke. flag is " + flag);

        Object ret = ctx.proceed();
        LOG.set(ret);
        return ret;
    }
}