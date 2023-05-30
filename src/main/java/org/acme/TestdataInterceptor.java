package org.acme;

import io.quarkus.arc.Priority;
import io.quarkus.logging.Log;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.concurrent.atomic.AtomicReference;

/*
https://github.com/quarkusio/quarkus/blob/2.16/independent-projects/arc/tests/src/test/java/io/quarkus/arc/test/interceptors/LoggingInterceptor.java
 */
@Testdata
@Priority(10)
@Interceptor
public class TestdataInterceptor {

    static final AtomicReference<Object> LOG = new AtomicReference<Object>();

    @AroundInvoke
    Object log(InvocationContext ctx) throws Exception {
        String testdataSql = ctx.getMethod().getAnnotation(Testdata.class).value();
        Log.info("Going to insert testdata from " + testdataSql);
//        Person.getEntityManager().createNativeQuery(Files.readString(classpath: testdataSql))... //TODO
        return ctx.proceed();
    }
}