package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.List;

/*
    idProperty1 and idProperty2 form a composite-key, supporting update of existing rows by saveOrUpdate
    adding id field to composite-key would lead to a new row even when a Download should be updated in-place
 */
@Entity
public class Download extends PanacheEntityBase {

    @Id
    public Long id;

    @Column
    public Boolean finished;


    public static String myFinder(){
        String searchKeyFromRemote = getSearchKeyFromRemote();
        //TODO perform query using searchKeyFromRemote
        return searchKeyFromRemote;
    }

    public static String getSearchKeyFromRemote(){
        return "real search key";
    }

    public Download() {
    }
}
