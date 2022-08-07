package org.acme.service;

import io.quarkus.arc.Arc;
import org.acme.SupportsJobType;
import org.acme.model.JobType;

public class Service {

    public Service() {
    }

    public String hello(){ return "";}

    public Service of(JobType jobType) {
        return Arc.container().instance(this.getClass(), new SupportsJobType.WithValue(jobType)).get();
    }
}
