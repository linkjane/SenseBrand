package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ReaderOld.
 */
@Entity
@Table(name = "reader_old")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "readerold")
public class ReaderOld implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "image_file")
    private byte[] imageFile;

    @Column(name = "image_file_content_type")
    private String imageFileContentType;

    @Lob
    @Column(name = "text_file")
    private String textFile;

    @NotNull
    @Lob
    @Column(name = "blob_file", nullable = false)
    private byte[] blobFile;

    @Column(name = "blob_file_content_type", nullable = false)
    private String blobFileContentType;

    @Column(name = "local_time")
    private LocalDate localTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImageFile() {
        return imageFile;
    }

    public ReaderOld imageFile(byte[] imageFile) {
        this.imageFile = imageFile;
        return this;
    }

    public void setImageFile(byte[] imageFile) {
        this.imageFile = imageFile;
    }

    public String getImageFileContentType() {
        return imageFileContentType;
    }

    public ReaderOld imageFileContentType(String imageFileContentType) {
        this.imageFileContentType = imageFileContentType;
        return this;
    }

    public void setImageFileContentType(String imageFileContentType) {
        this.imageFileContentType = imageFileContentType;
    }

    public String getTextFile() {
        return textFile;
    }

    public ReaderOld textFile(String textFile) {
        this.textFile = textFile;
        return this;
    }

    public void setTextFile(String textFile) {
        this.textFile = textFile;
    }

    public byte[] getBlobFile() {
        return blobFile;
    }

    public ReaderOld blobFile(byte[] blobFile) {
        this.blobFile = blobFile;
        return this;
    }

    public void setBlobFile(byte[] blobFile) {
        this.blobFile = blobFile;
    }

    public String getBlobFileContentType() {
        return blobFileContentType;
    }

    public ReaderOld blobFileContentType(String blobFileContentType) {
        this.blobFileContentType = blobFileContentType;
        return this;
    }

    public void setBlobFileContentType(String blobFileContentType) {
        this.blobFileContentType = blobFileContentType;
    }

    public LocalDate getLocalTime() {
        return localTime;
    }

    public ReaderOld localTime(LocalDate localTime) {
        this.localTime = localTime;
        return this;
    }

    public void setLocalTime(LocalDate localTime) {
        this.localTime = localTime;
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
        ReaderOld readerOld = (ReaderOld) o;
        if (readerOld.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), readerOld.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReaderOld{" +
            "id=" + getId() +
            ", imageFile='" + getImageFile() + "'" +
            ", imageFileContentType='" + getImageFileContentType() + "'" +
            ", textFile='" + getTextFile() + "'" +
            ", blobFile='" + getBlobFile() + "'" +
            ", blobFileContentType='" + getBlobFileContentType() + "'" +
            ", localTime='" + getLocalTime() + "'" +
            "}";
    }
}
