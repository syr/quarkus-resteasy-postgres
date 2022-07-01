package org.acme.service;


import org.acme.SupportsJobType;
import org.acme.model.JobType;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@SupportsJobType(JobType.B)
public class ServiceForB extends Service {
}
