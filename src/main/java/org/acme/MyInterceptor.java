package org.acme;

import io.quarkus.logging.Log;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@MyAnnotation
@Interceptor
public class MyInterceptor {
    @AroundInvoke
    Object log(InvocationContext ctx) throws Exception {
        if (ctx.getMethod().isAnnotationPresent(MyAnnotation.class)) {
            Log.info("MyAnnotation found");
        }
        boolean flag = ctx.getMethod().getAnnotation(MyAnnotation.class).value();

        Log.info("inside MyInterceptor @AroundInvoke. flag is " + flag);
        Object o = ctx.proceed();
        return o;
    }
}
