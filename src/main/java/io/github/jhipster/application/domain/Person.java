package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "file_1")
    private byte[] file1;

    @Column(name = "file_1_content_type")
    private String file1ContentType;

    @Lob
    @Column(name = "file_2")
    private byte[] file2;

    @Column(name = "file_2_content_type")
    private String file2ContentType;

    @Lob
    @Column(name = "file_3")
    private String file3;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFile1() {
        return file1;
    }

    public Person file1(byte[] file1) {
        this.file1 = file1;
        return this;
    }

    public void setFile1(byte[] file1) {
        this.file1 = file1;
    }

    public String getFile1ContentType() {
        return file1ContentType;
    }

    public Person file1ContentType(String file1ContentType) {
        this.file1ContentType = file1ContentType;
        return this;
    }

    public void setFile1ContentType(String file1ContentType) {
        this.file1ContentType = file1ContentType;
    }

    public byte[] getFile2() {
        return file2;
    }

    public Person file2(byte[] file2) {
        this.file2 = file2;
        return this;
    }

    public void setFile2(byte[] file2) {
        this.file2 = file2;
    }

    public String getFile2ContentType() {
        return file2ContentType;
    }

    public Person file2ContentType(String file2ContentType) {
        this.file2ContentType = file2ContentType;
        return this;
    }

    public void setFile2ContentType(String file2ContentType) {
        this.file2ContentType = file2ContentType;
    }

    public String getFile3() {
        return file3;
    }

    public Person file3(String file3) {
        this.file3 = file3;
        return this;
    }

    public void setFile3(String file3) {
        this.file3 = file3;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        if (person.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), person.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", file1='" + getFile1() + "'" +
            ", file1ContentType='" + getFile1ContentType() + "'" +
            ", file2='" + getFile2() + "'" +
            ", file2ContentType='" + getFile2ContentType() + "'" +
            ", file3='" + getFile3() + "'" +
            "}";
    }
}
