package org.acme.service;

import io.quarkus.arc.Arc;
import org.acme.SupportsJobType;
import org.acme.model.JobType;

public class ServiceLookup {
    public static  <T extends Service> T get(Class<T> clazz, JobType jobType){
        return Arc.container().instance(clazz, new SupportsJobType.Impl(jobType)).get();
    }
}
