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
    @Column(name = "img_fil", nullable = false)
    private String imgFil;

    @Column(name = "img_fil_url", nullable = false)
    private String imgFilUrl;

    @Column(name = "img_fil_content_type", nullable = false)
    private String imgFilContentType;

    @Lob
    @Column(name = "introduction")
    private String introduction;


    @ManyToOne
    private SolutionDetailAnalysis solutionDetailAnalysis;

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

    public String getImgFil() {
        return imgFil;
    }

    public SolutionDetailAnalysisImg imgFil(String imgFil) {
        this.imgFil = imgFil;
        return this;
    }

    public void setImgFil(String imgFil) {
        this.imgFil = imgFil;
    }

    public String getImgFilContentType() {
        return imgFilContentType;
    }


    public String getImgFilUrl() {
        return imgFilUrl;
    }



    public SolutionDetailAnalysisImg imgFilContentType(String imgFilContentType) {
        this.imgFilContentType = imgFilContentType;
        return this;
    }

    public void setImgFilContentType(String imgFilContentType) {
        this.imgFilContentType = imgFilContentType;
    }

    public void setImgFilUrl(String imgFilUrl) {
        this.imgFilUrl = imgFilUrl;
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

    public SolutionDetailAnalysis getSolutionDetailAnalysis() {
        return solutionDetailAnalysis;
    }

    public SolutionDetailAnalysisImg solutionDetailAnalysis(SolutionDetailAnalysis solutionDetailAnalysis) {
        this.solutionDetailAnalysis = solutionDetailAnalysis;
        return this;
    }

    public void setSolutionDetailAnalysis(SolutionDetailAnalysis solutionDetailAnalysis) {
        this.solutionDetailAnalysis = solutionDetailAnalysis;
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
