package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * A DesignerSentiment.
 */
@Entity
@Table(name = "designer_sentiment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "designersentiment")
public class DesignerSentiment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_level_title", nullable = false)
    private String firstLevelTitle;


    @Column(name = "second_level_title")
    private String secondLevelTitle;


    @NotNull
    @Column(name = "img_file", nullable = false)
    private String imgFile;

    @Column(name = "img_file_url", nullable = false)
    private String imgFileUrl;

    @Column(name = "img_file_content_type", nullable = false)
    private String imgFileContentType;

    @ManyToOne(optional = false)
    @NotNull
    private Designer designer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstLevelTitle() {
        return firstLevelTitle;
    }

    public DesignerSentiment firstLevelTitle(String firstLevelTitle) {
        this.firstLevelTitle = firstLevelTitle;
        return this;
    }

    public void setFirstLevelTitle(String firstLevelTitle) {
        this.firstLevelTitle = firstLevelTitle;
    }

    public String getSecondLevelTitle() {
        return secondLevelTitle;
    }

    public DesignerSentiment secondLevelTitle(String secondLevelTitle) {
        this.secondLevelTitle = secondLevelTitle;
        return this;
    }

    public void setSecondLevelTitle(String secondLevelTitle) {
        this.secondLevelTitle = secondLevelTitle;
    }

    public String getImgFile() {
        return imgFile;
    }

    public DesignerSentiment imgFile(String imgFile) {
        this.imgFile = imgFile;
        return this;
    }

    public void setImgFile(String imgFile) {
        this.imgFile = imgFile;
    }

    public String getImgFileContentType() {
        return imgFileContentType;
    }


    public String getImgFileUrl() {
        return imgFileUrl;
    }



    public DesignerSentiment imgFileContentType(String imgFileContentType) {
        this.imgFileContentType = imgFileContentType;
        return this;
    }

    public void setImgFileContentType(String imgFileContentType) {
        this.imgFileContentType = imgFileContentType;
    }

    public void setImgFileUrl(String imgFileUrl) {
        this.imgFileUrl = imgFileUrl;
    }


    public Designer getDesigner() {
        return designer;
    }

    public DesignerSentiment designer(Designer designer) {
        this.designer = designer;
        return this;
    }

    public void setDesigner(Designer designer) {
        this.designer = designer;
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
        DesignerSentiment designerSentiment = (DesignerSentiment) o;
        if (designerSentiment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), designerSentiment.getId());
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
