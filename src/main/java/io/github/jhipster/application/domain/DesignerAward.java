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
 * A DesignerAward.
 */
@Entity
@Table(name = "designer_award")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "designeraward")
public class DesignerAward implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;


    @Lob
    @Column(name = "introduction")
    private String introduction;


    @NotNull
    @Column(name = "img_file", nullable = false)
    private String imgFile;

    @Column(name = "img_file_url", nullable = false)
    private String imgFileUrl;

    @Column(name = "img_file_content_type", nullable = false)
    private String imgFileContentType;

    @Column(name = "detail_link")
    private String detailLink;


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

    public String getTitle() {
        return title;
    }

    public DesignerAward title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public DesignerAward introduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getImgFile() {
        return imgFile;
    }

    public DesignerAward imgFile(String imgFile) {
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



    public DesignerAward imgFileContentType(String imgFileContentType) {
        this.imgFileContentType = imgFileContentType;
        return this;
    }

    public void setImgFileContentType(String imgFileContentType) {
        this.imgFileContentType = imgFileContentType;
    }

    public void setImgFileUrl(String imgFileUrl) {
        this.imgFileUrl = imgFileUrl;
    }


    public String getDetailLink() {
        return detailLink;
    }

    public DesignerAward detailLink(String detailLink) {
        this.detailLink = detailLink;
        return this;
    }

    public void setDetailLink(String detailLink) {
        this.detailLink = detailLink;
    }

    public Designer getDesigner() {
        return designer;
    }

    public DesignerAward designer(Designer designer) {
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
        DesignerAward designerAward = (DesignerAward) o;
        if (designerAward.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), designerAward.getId());
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
