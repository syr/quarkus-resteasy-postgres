package org.acme.service;

import io.quarkus.arc.Unremovable;
import org.acme.SupportsJobType;
import org.acme.model.JobType;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Unremovable
@SupportsJobType(JobType.A)
public class ServiceForA extends Service {
    @Override
    public String hello() {
        return "hello from A";
    }
}
