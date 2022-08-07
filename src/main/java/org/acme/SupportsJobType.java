package org.acme;

import org.acme.model.JobType;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Qualifier;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
@Target({METHOD, FIELD, PARAMETER, TYPE})
public @interface SupportsJobType {
    JobType value();

    class Impl extends AnnotationLiteral<SupportsJobType> implements SupportsJobType {
        JobType requestedJobType;

        public Impl(JobType requestedJobType) {
            this.requestedJobType = requestedJobType;
        }

        @Override
        public JobType value() {
            return requestedJobType;
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return SupportsJobType.class;
        }
    }
}