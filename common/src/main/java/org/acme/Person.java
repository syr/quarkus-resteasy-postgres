package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.narayana.jta.QuarkusTransaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.transaction.SystemException;
import javax.transaction.Transactional;

@Entity
public class Person extends PanacheEntityBase {
    @Id
    @Column
    public Long id;
    public String firstname;
    public String lastname;

    public Person() {
    }

    public static void updateFirstnameStatic(String firstname){
        // @Transactional should not be used on static methods due to https://github.com/quarkusio/quarkus/issues/34005
        QuarkusTransaction.requiringNew().call(() -> update("firstname=?1 where id=1", firstname));
    }

    @Transactional
    public void updateFirstname(String firstnameArg) {
        firstname = firstnameArg;
    }

    public static Person findByIdForce(Long id){
        getEntityManager().clear();
        return findById(id);
    }
}
