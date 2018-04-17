package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MyFile.
 */
@Entity
@Table(name = "my_file")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "myfile")
public class MyFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "filename", nullable = false)
    private String filename;

    @Column(name = "my_file")
    private String myFile;

    @Lob
    @Column(name = "image_example")
    private byte[] imageExample;

    @Column(name = "image_example_content_type")
    private String imageExampleContentType;

    @Lob
    @Column(name = "test_example")
    private String testExample;

    @NotNull
    @Lob
    @Column(name = "text_file", nullable = false)
    private String textFile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public MyFile filename(String filename) {
        this.filename = filename;
        return this;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMyFile() {
        return myFile;
    }

    public MyFile myFile(String myFile) {
        this.myFile = myFile;
        return this;
    }

    public void setMyFile(String myFile) {
        this.myFile = myFile;
    }

    public byte[] getImageExample() {
        return imageExample;
    }

    public MyFile imageExample(byte[] imageExample) {
        this.imageExample = imageExample;
        return this;
    }

    public void setImageExample(byte[] imageExample) {
        this.imageExample = imageExample;
    }

    public String getImageExampleContentType() {
        return imageExampleContentType;
    }

    public MyFile imageExampleContentType(String imageExampleContentType) {
        this.imageExampleContentType = imageExampleContentType;
        return this;
    }

    public void setImageExampleContentType(String imageExampleContentType) {
        this.imageExampleContentType = imageExampleContentType;
    }

    public String getTestExample() {
        return testExample;
    }

    public MyFile testExample(String testExample) {
        this.testExample = testExample;
        return this;
    }

    public void setTestExample(String testExample) {
        this.testExample = testExample;
    }

    public String getTextFile() {
        return textFile;
    }

    public MyFile textFile(String textFile) {
        this.textFile = textFile;
        return this;
    }

    public void setTextFile(String textFile) {
        this.textFile = textFile;
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
        MyFile myFile = (MyFile) o;
        if (myFile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), myFile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MyFile{" +
            "id=" + getId() +
            ", filename='" + getFilename() + "'" +
            ", myFile='" + getMyFile() + "'" +
            ", imageExample='" + getImageExample() + "'" +
            ", imageExampleContentType='" + getImageExampleContentType() + "'" +
            ", testExample='" + getTestExample() + "'" +
            ", textFile='" + getTextFile() + "'" +
            "}";
    }
}
