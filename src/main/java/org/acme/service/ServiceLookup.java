package org.acme.service;

import io.quarkus.arc.Arc;
import org.acme.SupportsJobType;
import org.acme.model.JobType;

import javax.enterprise.util.TypeLiteral;

public class ServiceLookup {
    public static <T extends Service> T get(Class<T> clazz, JobType jobType){
        return Arc.container().instance(clazz, new SupportsJobType.Impl(jobType)).get();
    }

    public static <T extends Service> T get2(JobType jobType){
        return Arc.container().instance(new TypeLiteral<T>() {}, new SupportsJobType.Impl(jobType)).get();
    }
}
