package org.acme.service;

public class ServiceLookup<T> {

    private T t;

    public ServiceLookup() {
    }

    public ServiceLookup(T t) {
        this.t = t;
    }

//    public T of(JobType jobType) {
//        return (T) Arc.container().instance(new TypeLiteral<T>, new SupportsJobType.WithValue(jobType)).get();
//    }
}
