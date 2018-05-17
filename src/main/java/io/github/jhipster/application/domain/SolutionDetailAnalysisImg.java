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
 * A SolutionDetailAnalysisImg.
 */
@Entity
@Table(name = "solution_detail_analysis_img")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "solutiondetailanalysisimg")
public class SolutionDetailAnalysisImg implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;


    @NotNull
    @Column(name = "img_file", nullable = false)
    private String imgFile;

    @Column(name = "img_file_url", nullable = false)
    private String imgFileUrl;

    @Column(name = "img_file_content_type", nullable = false)
    private String imgFileContentType;

    @Lob
    @Column(name = "introduction")
    private String introduction;


    @ManyToOne
    private SolutionDetailAnalysis detailAnalysis;

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

    public SolutionDetailAnalysisImg title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgFile() {
        return imgFile;
    }

    public SolutionDetailAnalysisImg imgFile(String imgFile) {
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



    public SolutionDetailAnalysisImg imgFileContentType(String imgFileContentType) {
        this.imgFileContentType = imgFileContentType;
        return this;
    }

    public void setImgFileContentType(String imgFileContentType) {
        this.imgFileContentType = imgFileContentType;
    }

    public void setImgFileUrl(String imgFileUrl) {
        this.imgFileUrl = imgFileUrl;
    }


    public String getIntroduction() {
        return introduction;
    }

    public SolutionDetailAnalysisImg introduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public SolutionDetailAnalysis getDetailAnalysis() {
        return detailAnalysis;
    }

    public SolutionDetailAnalysisImg detailAnalysis(SolutionDetailAnalysis solutionDetailAnalysis) {
        this.detailAnalysis = solutionDetailAnalysis;
        return this;
    }

    public void setDetailAnalysis(SolutionDetailAnalysis solutionDetailAnalysis) {
        this.detailAnalysis = solutionDetailAnalysis;
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
        SolutionDetailAnalysisImg solutionDetailAnalysisImg = (SolutionDetailAnalysisImg) o;
        if (solutionDetailAnalysisImg.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), solutionDetailAnalysisImg.getId());
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
