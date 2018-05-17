package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * A DesignerIdeaMedia.
 */
@Entity
@Table(name = "designer_idea_media")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "designerideamedia")
public class DesignerIdeaMedia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;


    @NotNull
    @Column(name = "share_time", nullable = false)
    private LocalDate shareTime;


    @Lob
    @Column(name = "introduction")
    private String introduction;


    @NotNull
    @Column(name = "media_file", nullable = false)
    private String mediaFile;

    @Column(name = "media_file_url", nullable = false)
    private String mediaFileUrl;

    @Column(name = "media_file_content_type", nullable = false)
    private String mediaFileContentType;

    @Column(name = "is_show")
    private Boolean isShow;


    @ManyToOne
    private Designer designer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public DesignerIdeaMedia title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getShareTime() {
        return shareTime;
    }

    public DesignerIdeaMedia shareTime(LocalDate shareTime) {
        this.shareTime = shareTime;
        return this;
    }

    public void setShareTime(LocalDate shareTime) {
        this.shareTime = shareTime;
    }

    public String getIntroduction() {
        return introduction;
    }

    public DesignerIdeaMedia introduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMediaFile() {
        return mediaFile;
    }

    public DesignerIdeaMedia mediaFile(String mediaFile) {
        this.mediaFile = mediaFile;
        return this;
    }

    public void setMediaFile(String mediaFile) {
        this.mediaFile = mediaFile;
    }

    public String getMediaFileContentType() {
        return mediaFileContentType;
    }


    public String getMediaFileUrl() {
        return mediaFileUrl;
    }



    public DesignerIdeaMedia mediaFileContentType(String mediaFileContentType) {
        this.mediaFileContentType = mediaFileContentType;
        return this;
    }

    public void setMediaFileContentType(String mediaFileContentType) {
        this.mediaFileContentType = mediaFileContentType;
    }

    public void setMediaFileUrl(String mediaFileUrl) {
        this.mediaFileUrl = mediaFileUrl;
    }


    public Boolean isIsShow() {
        return isShow;
    }

    public DesignerIdeaMedia isShow(Boolean isShow) {
        this.isShow = isShow;
        return this;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public Designer getDesigner() {
        return designer;
    }

    public DesignerIdeaMedia designer(Designer designer) {
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
        DesignerIdeaMedia designerIdeaMedia = (DesignerIdeaMedia) o;
        if (designerIdeaMedia.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), designerIdeaMedia.getId());
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
