package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class FilePart extends PanacheEntityBase implements Serializable {
    @Id
    @SequenceGenerator(name = "filepart_id_seq", sequenceName = "filepart_id_seq", allocationSize = 1, initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filepart_id_seq")
    public Long id;

    public String filePartFilePath;

    public FilePart() {
    }

    public FilePart(String filePartFilePath) {
        this.filePartFilePath = filePartFilePath;
    }
}
