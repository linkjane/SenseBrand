package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


/**
 * A SolutionDetailAnalysis.
 */
@Entity
@Table(name = "solution_detail_analysis")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "solutiondetailanalysis")
public class SolutionDetailAnalysis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;


    @Lob
    @Column(name = "introduction")
    private String introduction;


    @OneToMany(mappedBy = "detailAnalysis")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SolutionDetailAnalysisImg> detailAnalysisImgs = new HashSet<>();

    @ManyToOne
    private Solution solution;

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

    public SolutionDetailAnalysis title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public SolutionDetailAnalysis introduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Set<SolutionDetailAnalysisImg> getDetailAnalysisImgs() {
        return detailAnalysisImgs;
    }

    public SolutionDetailAnalysis detailAnalysisImgs(Set<SolutionDetailAnalysisImg> solutionDetailAnalysisImgs) {
        this.detailAnalysisImgs = solutionDetailAnalysisImgs;
        return this;
    }

    public SolutionDetailAnalysis addDetailAnalysisImg(SolutionDetailAnalysisImg solutionDetailAnalysisImg) {
        this.detailAnalysisImgs.add(solutionDetailAnalysisImg);
        solutionDetailAnalysisImg.setDetailAnalysis(this);
        return this;
    }

    public SolutionDetailAnalysis removeDetailAnalysisImg(SolutionDetailAnalysisImg solutionDetailAnalysisImg) {
        this.detailAnalysisImgs.remove(solutionDetailAnalysisImg);
        solutionDetailAnalysisImg.setDetailAnalysis(null);
        return this;
    }

    public void setDetailAnalysisImgs(Set<SolutionDetailAnalysisImg> solutionDetailAnalysisImgs) {
        this.detailAnalysisImgs = solutionDetailAnalysisImgs;
    }

    public Solution getSolution() {
        return solution;
    }

    public SolutionDetailAnalysis solution(Solution solution) {
        this.solution = solution;
        return this;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
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
        SolutionDetailAnalysis solutionDetailAnalysis = (SolutionDetailAnalysis) o;
        if (solutionDetailAnalysis.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), solutionDetailAnalysis.getId());
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
