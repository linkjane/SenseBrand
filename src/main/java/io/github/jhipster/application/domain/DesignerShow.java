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
 * A DesignerShow.
 */
@Entity
@Table(name = "designer_show")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "designershow")
public class DesignerShow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_level_title", nullable = false)
    private String firstLevelTitle;


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

    public String getFirstLevelTitle() {
        return firstLevelTitle;
    }

    public DesignerShow firstLevelTitle(String firstLevelTitle) {
        this.firstLevelTitle = firstLevelTitle;
        return this;
    }

    public void setFirstLevelTitle(String firstLevelTitle) {
        this.firstLevelTitle = firstLevelTitle;
    }

    public String getSecondLevelTitle() {
        return secondLevelTitle;
    }

    public DesignerShow secondLevelTitle(String secondLevelTitle) {
        this.secondLevelTitle = secondLevelTitle;
        return this;
    }

    public void setSecondLevelTitle(String secondLevelTitle) {
        this.secondLevelTitle = secondLevelTitle;
    }

    public String getIntroduction() {
        return introduction;
    }

    public DesignerShow introduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Designer getDesigner() {
        return designer;
    }

    public DesignerShow designer(Designer designer) {
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
        DesignerShow designerShow = (DesignerShow) o;
        if (designerShow.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), designerShow.getId());
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
