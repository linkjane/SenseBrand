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
 * A DesignerIdeaDetails.
 */
@Entity
@Table(name = "designer_idea_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "designerideadetails")
public class DesignerIdeaDetails implements Serializable {

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


    @Column(name = "second_level_title")
    private String secondLevelTitle;


    @Lob
    @Column(name = "introduction")
    private String introduction;


    @OneToOne
    @JoinColumn(unique = true)
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

    public DesignerIdeaDetails title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getShareTime() {
        return shareTime;
    }

    public DesignerIdeaDetails shareTime(LocalDate shareTime) {
        this.shareTime = shareTime;
        return this;
    }

    public void setShareTime(LocalDate shareTime) {
        this.shareTime = shareTime;
    }

    public String getSecondLevelTitle() {
        return secondLevelTitle;
    }

    public DesignerIdeaDetails secondLevelTitle(String secondLevelTitle) {
        this.secondLevelTitle = secondLevelTitle;
        return this;
    }

    public void setSecondLevelTitle(String secondLevelTitle) {
        this.secondLevelTitle = secondLevelTitle;
    }

    public String getIntroduction() {
        return introduction;
    }

    public DesignerIdeaDetails introduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Designer getDesigner() {
        return designer;
    }

    public DesignerIdeaDetails designer(Designer designer) {
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
        DesignerIdeaDetails designerIdeaDetails = (DesignerIdeaDetails) o;
        if (designerIdeaDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), designerIdeaDetails.getId());
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
