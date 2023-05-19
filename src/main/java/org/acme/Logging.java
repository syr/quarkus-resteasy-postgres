package org.acme;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

/*
https://github.com/quarkusio/quarkus/blob/2.16/independent-projects/arc/tests/src/test/java/io/quarkus/arc/test/interceptors/Logging.java
 */
@Target({ TYPE, METHOD })
@Retention(RUNTIME)
@Documented
@InterceptorBinding
public @interface Logging {
    boolean value() default false;
}