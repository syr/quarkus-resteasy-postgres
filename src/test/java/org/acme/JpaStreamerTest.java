package org.acme;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.field.StringField;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.panache.common.Parameters;
import io.quarkus.test.junit.QuarkusTest;
import org.acme.entity.Person;
import org.acme.entity.Person$;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class JpaStreamerTest {
    @Inject
    JPAStreamer jpaStreamer;

    @Test
    public void test() {
        createPerson(1);
        createPerson(2);

//        Panache needs all predicates be passed at the first method
        List<Person> personListOld = Person.<Person>streamAll()
                .filter(person -> person.firstname.contains("first"))
                .filter(person -> person.firstname.contains("last"))
                .toList();

//        JPAStreamer pushes down all filters to the query
        StringField<Person> firstname = Person$.firstname;
        List<Person> personList = jpaStreamer.stream(Person.class)
                .filter(firstname.contains("first"))
                .filter(firstname.contains("name"))
                .filter(firstname.equal("firstname1").or(firstname.equal("firstname2")))
                .sorted(firstname.comparator().reversed())
                .toList();

        assertEquals(2,personList.size());

        //update by Panache
        updatePersons("firstnameUpdated");

        List<Person> personsUpdated = QuarkusTransaction.requiringNew().call(() -> Person.listAll());

        String s ="";
    }

    @Transactional
    protected void updatePersons(String newFirstname) {
        Parameters params = Parameters
//                .with("firstnameCol", Person$.firstname.columnName()) //FIXME param binding seems to work only for param values, not column names
                .with("firstnameValue", newFirstname);

        Person.update(Person$.firstname.columnName() + " = :firstnameValue", params);

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
