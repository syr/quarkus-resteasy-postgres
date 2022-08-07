package org.acme.service;

import io.quarkus.arc.Arc;
import org.acme.SupportsJobType;
import org.acme.model.JobType;

public class ServiceLookup<T extends Service> {

    Class<T> clazz;

    public ServiceLookup(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T with(JobType jobType){
        return Arc.container().instance(clazz, new SupportsJobType.WithValue(jobType)).get();
    }

    public String hello() {
        return "";
    }
}
