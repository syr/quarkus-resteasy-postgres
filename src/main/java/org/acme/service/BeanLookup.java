package org.acme.service;

import io.quarkus.arc.Arc;
import org.acme.SupportsJobType;
import org.acme.model.JobType;

import javax.enterprise.util.TypeLiteral;
import javax.management.InstanceNotFoundException;
import java.util.Optional;

public class BeanLookup {
    public static <T extends Service> T get(Class<T> clazz, JobType jobType) throws InstanceNotFoundException {
        T t = Arc.container().instance(clazz, new SupportsJobType.Impl(jobType)).get();

        if (Optional.ofNullable(t).isEmpty()) {
            throw new InstanceNotFoundException("");
        }

        return t;
    }

    public static <T extends Service> T get2(JobType jobType){
        return Arc.container().instance(new TypeLiteral<T>() {}, new SupportsJobType.Impl(jobType)).get();
    }
}
