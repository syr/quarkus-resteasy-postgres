package org.acme;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public abstract class SimpleSourceDestinationMapper {


    abstract SimpleDestination sourceToDestination(SimpleSource source, AdditionalSource additionalSource);

    @AfterMapping
    public void afterMappingOfSourceToDestination(SimpleSource source, AdditionalSource additionalSource, @MappingTarget SimpleDestination simpleDestination) {
        simpleDestination.name = addTimezone(simpleDestination.name, additionalSource);
    }



    abstract SimpleDestination2 sourceToDestination2(SimpleSource2 source, AdditionalSource additionalSource);

    @AfterMapping
    public void afterMappingOfSource2ToDestination2(SimpleSource2 source, AdditionalSource additionalSource, @MappingTarget SimpleDestination2 simpleDestination) {
        simpleDestination.name = addTimezone(simpleDestination.name, additionalSource);
        simpleDestination.description = addId(simpleDestination.description, additionalSource);
    }


    public String addTimezone(String name, AdditionalSource additionalSource ){
        return name + additionalSource.timezone;
    }

    public String addId(String name, AdditionalSource additionalSource ){
        return name + additionalSource.id;
    }



}