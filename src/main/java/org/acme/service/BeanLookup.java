package org.acme.service;

import io.quarkus.arc.Arc;
import org.acme.SupportsJobType;
import org.acme.model.JobType;

import javax.enterprise.util.TypeLiteral;
import javax.management.InstanceNotFoundException;
import java.util.Optional;

public class BeanLookup {
    public static <T extends Service> T getInstance(Class<T> clazz, JobType jobType) throws InstanceNotFoundException {
        T instance = Arc.container().instance(clazz, new SupportsJobType.Impl(jobType)).get();

        if (Optional.ofNullable(instance).isEmpty()) {
            throw new InstanceNotFoundException("");
        }

        return instance;
    }

    public static <T extends Service> T get2(JobType jobType){
        return Arc.container().instance(new TypeLiteral<T>() {}, new SupportsJobType.Impl(jobType)).get();
    }
}
