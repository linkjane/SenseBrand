package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * A Solution.
 */
@Entity
@Table(name = "solution")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "solution")
public class Solution implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;


    @NotNull
    @Lob
    @Column(name = "introduction", nullable = false)
    private String introduction;


    @Column(name = "banner_img_file")
    private String bannerImgFile;

    @Column(name = "banner_img_file_url")
    private String bannerImgFileUrl;

    @Column(name = "banner_img_file_content_type")
    private String bannerImgFileContentType;

    @NotNull
    @Lob
    @Column(name = "banner_introduction", nullable = false)
    private String bannerIntroduction;


    @NotNull
    @Column(name = "summarize_title", nullable = false)
    private String summarizeTitle;


    @Column(name = "summarize_img_file")
    private String summarizeImgFile;

    @Column(name = "summarize_img_file_url")
    private String summarizeImgFileUrl;

    @Column(name = "summarize_img_file_content_type")
    private String summarizeImgFileContentType;

    @NotNull
    @Lob
    @Column(name = "summarize_content", nullable = false)
    private String summarizeContent;


    @NotNull
    @Column(name = "significance_title", nullable = false)
    private String significanceTitle;


    @Column(name = "significance_img_file")
    private String significanceImgFile;

    @Column(name = "significance_img_file_url")
    private String significanceImgFileUrl;

    @Column(name = "significance_img_file_content_type")
    private String significanceImgFileContentType;

    @NotNull
    @Lob
    @Column(name = "significance_introduction", nullable = false)
    private String significanceIntroduction;


    @NotNull
    @Column(name = "detail_analysis_title", nullable = false)
    private String detailAnalysisTitle;


    @Column(name = "is_show")
    private Boolean isShow;


    @OneToMany(mappedBy = "solution")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SolutionDetailImg> detailImgs = new HashSet<>();

    @OneToMany(mappedBy = "solution")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SolutionDetailAnalysis> detailAnalyses = new HashSet<>();

    @OneToMany(mappedBy = "solution")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SolutionCorrelation> correlations = new HashSet<>();

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

    public Solution title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public Solution introduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getBannerImgFile() {
        return bannerImgFile;
    }

    public Solution bannerImgFile(String bannerImgFile) {
        this.bannerImgFile = bannerImgFile;
        return this;
    }

    public void setBannerImgFile(String bannerImgFile) {
        this.bannerImgFile = bannerImgFile;
    }

    public String getBannerImgFileContentType() {
        return bannerImgFileContentType;
    }


    public String getBannerImgFileUrl() {
        return bannerImgFileUrl;
    }



    public Solution bannerImgFileContentType(String bannerImgFileContentType) {
        this.bannerImgFileContentType = bannerImgFileContentType;
        return this;
    }

    public void setBannerImgFileContentType(String bannerImgFileContentType) {
        this.bannerImgFileContentType = bannerImgFileContentType;
    }

    public void setBannerImgFileUrl(String bannerImgFileUrl) {
        this.bannerImgFileUrl = bannerImgFileUrl;
    }


    public String getBannerIntroduction() {
        return bannerIntroduction;
    }

    public Solution bannerIntroduction(String bannerIntroduction) {
        this.bannerIntroduction = bannerIntroduction;
        return this;
    }

    public void setBannerIntroduction(String bannerIntroduction) {
        this.bannerIntroduction = bannerIntroduction;
    }

    public String getSummarizeTitle() {
        return summarizeTitle;
    }

    public Solution summarizeTitle(String summarizeTitle) {
        this.summarizeTitle = summarizeTitle;
        return this;
    }

    public void setSummarizeTitle(String summarizeTitle) {
        this.summarizeTitle = summarizeTitle;
    }

    public String getSummarizeImgFile() {
        return summarizeImgFile;
    }

    public Solution summarizeImgFile(String summarizeImgFile) {
        this.summarizeImgFile = summarizeImgFile;
        return this;
    }

    public void setSummarizeImgFile(String summarizeImgFile) {
        this.summarizeImgFile = summarizeImgFile;
    }

    public String getSummarizeImgFileContentType() {
        return summarizeImgFileContentType;
    }


    public String getSummarizeImgFileUrl() {
        return summarizeImgFileUrl;
    }



    public Solution summarizeImgFileContentType(String summarizeImgFileContentType) {
        this.summarizeImgFileContentType = summarizeImgFileContentType;
        return this;
    }

    public void setSummarizeImgFileContentType(String summarizeImgFileContentType) {
        this.summarizeImgFileContentType = summarizeImgFileContentType;
    }

    public void setSummarizeImgFileUrl(String summarizeImgFileUrl) {
        this.summarizeImgFileUrl = summarizeImgFileUrl;
    }


    public String getSummarizeContent() {
        return summarizeContent;
    }

    public Solution summarizeContent(String summarizeContent) {
        this.summarizeContent = summarizeContent;
        return this;
    }

    public void setSummarizeContent(String summarizeContent) {
        this.summarizeContent = summarizeContent;
    }

    public String getSignificanceTitle() {
        return significanceTitle;
    }

    public Solution significanceTitle(String significanceTitle) {
        this.significanceTitle = significanceTitle;
        return this;
    }

    public void setSignificanceTitle(String significanceTitle) {
        this.significanceTitle = significanceTitle;
    }

    public String getSignificanceImgFile() {
        return significanceImgFile;
    }

    public Solution significanceImgFile(String significanceImgFile) {
        this.significanceImgFile = significanceImgFile;
        return this;
    }

    public void setSignificanceImgFile(String significanceImgFile) {
        this.significanceImgFile = significanceImgFile;
    }

    public String getSignificanceImgFileContentType() {
        return significanceImgFileContentType;
    }


    public String getSignificanceImgFileUrl() {
        return significanceImgFileUrl;
    }



    public Solution significanceImgFileContentType(String significanceImgFileContentType) {
        this.significanceImgFileContentType = significanceImgFileContentType;
        return this;
    }

    public void setSignificanceImgFileContentType(String significanceImgFileContentType) {
        this.significanceImgFileContentType = significanceImgFileContentType;
    }

    public void setSignificanceImgFileUrl(String significanceImgFileUrl) {
        this.significanceImgFileUrl = significanceImgFileUrl;
    }


    public String getSignificanceIntroduction() {
        return significanceIntroduction;
    }

    public Solution significanceIntroduction(String significanceIntroduction) {
        this.significanceIntroduction = significanceIntroduction;
        return this;
    }

    public void setSignificanceIntroduction(String significanceIntroduction) {
        this.significanceIntroduction = significanceIntroduction;
    }

    public String getDetailAnalysisTitle() {
        return detailAnalysisTitle;
    }

    public Solution detailAnalysisTitle(String detailAnalysisTitle) {
        this.detailAnalysisTitle = detailAnalysisTitle;
        return this;
    }

    public void setDetailAnalysisTitle(String detailAnalysisTitle) {
        this.detailAnalysisTitle = detailAnalysisTitle;
    }

    public Boolean isIsShow() {
        return isShow;
    }

    public Solution isShow(Boolean isShow) {
        this.isShow = isShow;
        return this;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public Set<SolutionDetailImg> getDetailImgs() {
        return detailImgs;
    }

    public Solution detailImgs(Set<SolutionDetailImg> solutionDetailImgs) {
        this.detailImgs = solutionDetailImgs;
        return this;
    }

    public Solution addDetailImg(SolutionDetailImg solutionDetailImg) {
        this.detailImgs.add(solutionDetailImg);
        solutionDetailImg.setSolution(this);
        return this;
    }

    public Solution removeDetailImg(SolutionDetailImg solutionDetailImg) {
        this.detailImgs.remove(solutionDetailImg);
        solutionDetailImg.setSolution(null);
        return this;
    }

    public void setDetailImgs(Set<SolutionDetailImg> solutionDetailImgs) {
        this.detailImgs = solutionDetailImgs;
    }

    public Set<SolutionDetailAnalysis> getDetailAnalyses() {
        return detailAnalyses;
    }

    public Solution detailAnalyses(Set<SolutionDetailAnalysis> solutionDetailAnalyses) {
        this.detailAnalyses = solutionDetailAnalyses;
        return this;
    }

    public Solution addDetailAnalysis(SolutionDetailAnalysis solutionDetailAnalysis) {
        this.detailAnalyses.add(solutionDetailAnalysis);
        solutionDetailAnalysis.setSolution(this);
        return this;
    }

    public Solution removeDetailAnalysis(SolutionDetailAnalysis solutionDetailAnalysis) {
        this.detailAnalyses.remove(solutionDetailAnalysis);
        solutionDetailAnalysis.setSolution(null);
        return this;
    }

    public void setDetailAnalyses(Set<SolutionDetailAnalysis> solutionDetailAnalyses) {
        this.detailAnalyses = solutionDetailAnalyses;
    }

    public Set<SolutionCorrelation> getCorrelations() {
        return correlations;
    }

    public Solution correlations(Set<SolutionCorrelation> solutionCorrelations) {
        this.correlations = solutionCorrelations;
        return this;
    }

    public Solution addCorrelation(SolutionCorrelation solutionCorrelation) {
        this.correlations.add(solutionCorrelation);
        solutionCorrelation.setSolution(this);
        return this;
    }

    public Solution removeCorrelation(SolutionCorrelation solutionCorrelation) {
        this.correlations.remove(solutionCorrelation);
        solutionCorrelation.setSolution(null);
        return this;
    }

    public void setCorrelations(Set<SolutionCorrelation> solutionCorrelations) {
        this.correlations = solutionCorrelations;
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
        Solution solution = (Solution) o;
        if (solution.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), solution.getId());
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
