package org.acme;

import io.quarkus.arc.Priority;
import io.quarkus.logging.Log;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;

/*
https://github.com/quarkusio/quarkus/blob/2.16/independent-projects/arc/tests/src/test/java/io/quarkus/arc/test/interceptors/LoggingInterceptor.java
 */
@Testdata
@Priority(10)
@Interceptor
public class TestdataInterceptor {

    static final AtomicReference<Object> LOG = new AtomicReference<Object>();
    public static final String TESTDATA_ROOT_DIR = "testdata";

    @AroundInvoke
    Object log(InvocationContext ctx) throws Exception {
        String testdataSqlFileName = ctx.getMethod().getAnnotation(Testdata.class).value();

        if(testdataSqlFileName.isEmpty()) { //default to test method name + .sql if no sql file name was passed by annoation
            String testMethodName = ctx.getMethod().getName();
            testdataSqlFileName = testMethodName + ".sql";
        }

        Log.info("Going to insert testdata from " + testdataSqlFileName);

        URL sqlFileUrl = ctx.getClass().getClassLoader().getResource(TESTDATA_ROOT_DIR  + File.separator + testdataSqlFileName);
        String sqlFileContent = Files.readString(Path.of(sqlFileUrl.toURI()));
        Log.info("testdata sql " + sqlFileContent);

//        Person.getEntityManager().createNativeQuery(sqlFileContent)... //TODO

        return ctx.proceed();
    }
}