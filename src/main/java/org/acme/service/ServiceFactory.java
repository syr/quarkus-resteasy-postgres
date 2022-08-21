package org.acme.service;

import org.acme.model.JobType;

import javax.enterprise.context.ApplicationScoped;
import javax.management.InstanceNotFoundException;

@ApplicationScoped
public class ServiceFactory {
    public static Service getFor(JobType jobType) throws InstanceNotFoundException {
        return BeanLookup.get(Service.class, jobType);
    }
}
