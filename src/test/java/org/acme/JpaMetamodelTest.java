package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.entity.Person;
import org.acme.entity.Person_;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;

@QuarkusTest
public class JpaMetamodelTest {

    @Test
    public void test() {
        createPerson(1);
        createPerson(2);
        updatePersons();

        String s="";
    }

    @Transactional
    protected void updatePersons() {
        Person.update(Person_.FIRSTNAME + " = 'firstnameUpdated'");
    }

    @Transactional
    protected void createPerson(int n) {
        Person p = new Person();
        p.id= (long) n;
        p.firstname = "firstname"+n;
        p.lastname = "lastname"+n;
        Person.persist(p);
    }
}
