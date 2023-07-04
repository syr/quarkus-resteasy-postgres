package org.acme.quartz;

import java.time.Instant;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "TASKS")
public class Task extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TASKS_SEQ")
    @SequenceGenerator(name="TASKS_SEQ", sequenceName = "TASKS_SEQ", allocationSize=1)
    private Long id;

    public Instant createdAt;

    public Task() {
        createdAt = Instant.now();
    }

    public Task(Instant time) {
        this.createdAt = time;
    }
}
