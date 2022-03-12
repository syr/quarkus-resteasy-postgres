package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class FilePart extends PanacheEntityBase implements Serializable {
    @Id
    @SequenceGenerator(name = "filepart_id_seq", sequenceName = "filepart_id_seq", allocationSize = 1, initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filepart_id_seq")
    public Long id;

    public String idProperty1;

    public String idProperty2;

    public String filePartFilePath;

    //adds fk fields idProperty1, idProperty2 to FilePart table
    // ==> FIXME maybe conflicting with existing fields idProperty1, idProperty2
    //BEWARE: all @Id fields of Download must be included
    //examples: https://www.baeldung.com/jpa-join-column
    @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumns({
                    @JoinColumn(referencedColumnName = "idProperty1", name = "idProperty1", insertable = false, updatable = false),
                    @JoinColumn(referencedColumnName = "idProperty2", name = "idProperty2", insertable = false, updatable = false)
//                    @JoinColumn(referencedColumnName = "id", name = "downloadId")
            })
    Download download;



    public FilePart() {
    }

    public FilePart(String idProperty1, String idProperty2, String filePartFilePath) {
        this.idProperty1 = idProperty1;
        this.idProperty2 = idProperty2;
        this.filePartFilePath = filePartFilePath;
    }
}
