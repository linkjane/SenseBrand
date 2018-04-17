package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * A Test1.
 */
@Entity
@Table(name = "test1")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "test1")
public class Test1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "test")
    private String test;


    @Column(name = "n")
    private String n;


    @Column(name = "sdf")
    private String sdf;


    @Column(name = "image_file")
    private String imageFile;

    @Column(name = "image_file_url")
    private String imageFileUrl;

    @Column(name = "image_file_content_type")
    private String imageFileContentType;

    @Column(name = "blob_file")
    private String blobFile;

    @Column(name = "blob_file_url")
    private String blobFileUrl;

    @Column(name = "blob_file_content_type")
    private String blobFileContentType;

    @Column(name = "text_file")
    private String textFile;


    @Lob
    @Column(name = "text_file_test")
    private String textFileTest;


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTest() {
        return test;
    }

    public Test1 test(String test) {
        this.test = test;
        return this;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getN() {
        return n;
    }

    public Test1 n(String n) {
        this.n = n;
        return this;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getSdf() {
        return sdf;
    }

    public Test1 sdf(String sdf) {
        this.sdf = sdf;
        return this;
    }

    public void setSdf(String sdf) {
        this.sdf = sdf;
    }

    public String getImageFile() {
        return imageFile;
    }

    public Test1 imageFile(String imageFile) {
        this.imageFile = imageFile;
        return this;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getImageFileContentType() {
        return imageFileContentType;
    }


    public String getImageFileUrl() {
        return imageFileUrl;
    }



    public Test1 imageFileContentType(String imageFileContentType) {
        this.imageFileContentType = imageFileContentType;
        return this;
    }

    public void setImageFileContentType(String imageFileContentType) {
        this.imageFileContentType = imageFileContentType;
    }

    public void setImageFileUrl(String imageFileUrl) {
        this.imageFileUrl = imageFileUrl;
    }


    public String getBlobFile() {
        return blobFile;
    }

    public Test1 blobFile(String blobFile) {
        this.blobFile = blobFile;
        return this;
    }

    public void setBlobFile(String blobFile) {
        this.blobFile = blobFile;
    }

    public String getBlobFileContentType() {
        return blobFileContentType;
    }


    public String getBlobFileUrl() {
        return blobFileUrl;
    }



    public Test1 blobFileContentType(String blobFileContentType) {
        this.blobFileContentType = blobFileContentType;
        return this;
    }

    public void setBlobFileContentType(String blobFileContentType) {
        this.blobFileContentType = blobFileContentType;
    }

    public void setBlobFileUrl(String blobFileUrl) {
        this.blobFileUrl = blobFileUrl;
    }


    public String getTextFile() {
        return textFile;
    }

    public Test1 textFile(String textFile) {
        this.textFile = textFile;
        return this;
    }

    public void setTextFile(String textFile) {
        this.textFile = textFile;
    }

    public String getTextFileTest() {
        return textFileTest;
    }

    public Test1 textFileTest(String textFileTest) {
        this.textFileTest = textFileTest;
        return this;
    }

    public void setTextFileTest(String textFileTest) {
        this.textFileTest = textFileTest;
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
        Test1 test1 = (Test1) o;
        if (test1.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), test1.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return "Reader is " + mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "error parse Object to json";
    }
}
