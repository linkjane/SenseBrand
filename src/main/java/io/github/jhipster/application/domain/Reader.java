package io.github.jhipster.application.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;



import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A Reader.
 */
@Entity
@Table(name = "reader")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "reader")
public class Reader implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_file")
    private String imageFile;

    @Column(name = "image_file_url")
    private String imageFileUrl;

    @Column(name = "image_file_content_type")
    private String imageFileContentType;

    @Lob
    @Column(name = "text_file")
    private String textFile;


    @Column(name = "blob_file")
    private String blobFile;

    @Column(name = "blob_file_url")
    private String blobFileUrl;

    @Column(name = "blob_file_content_type")
    private String blobFileContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageFile() {
        return imageFile;
    }

    public Reader imageFile(String imageFile) {
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



    public Reader imageFileContentType(String imageFileContentType) {
        this.imageFileContentType = imageFileContentType;
        return this;
    }

    public void setImageFileContentType(String imageFileContentType) {
        this.imageFileContentType = imageFileContentType;
    }

    public void setImageFileUrl(String imageFileUrl) {
        this.imageFileUrl = imageFileUrl;
    }


    public String getTextFile() {
        return textFile;
    }

    public Reader textFile(String textFile) {
        this.textFile = textFile;
        return this;
    }

    public void setTextFile(String textFile) {
        this.textFile = textFile;
    }

    public String getBlobFile() {
        return blobFile;
    }

    public Reader blobFile(String blobFile) {
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



    public Reader blobFileContentType(String blobFileContentType) {
        this.blobFileContentType = blobFileContentType;
        return this;
    }

    public void setBlobFileContentType(String blobFileContentType) {
        this.blobFileContentType = blobFileContentType;
    }

    public void setBlobFileUrl(String blobFileUrl) {
        this.blobFileUrl = blobFileUrl;
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
        Reader reader = (Reader) o;
        if (reader.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reader.getId());
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
