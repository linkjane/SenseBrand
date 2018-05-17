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
 * A IndustryFirstClass.
 */
@Entity
@Table(name = "industry_first_class")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "industryfirstclass")
public class IndustryFirstClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;


    @OneToMany(mappedBy = "industryFirstClass")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IndustrySecondClass> industrySecondClasses = new HashSet<>();

    @ManyToOne
    private IndustryAll industryAll;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public IndustryFirstClass name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<IndustrySecondClass> getIndustrySecondClasses() {
        return industrySecondClasses;
    }

    public IndustryFirstClass industrySecondClasses(Set<IndustrySecondClass> industrySecondClasses) {
        this.industrySecondClasses = industrySecondClasses;
        return this;
    }

    public IndustryFirstClass addIndustrySecondClass(IndustrySecondClass industrySecondClass) {
        this.industrySecondClasses.add(industrySecondClass);
        industrySecondClass.setIndustryFirstClass(this);
        return this;
    }

    public IndustryFirstClass removeIndustrySecondClass(IndustrySecondClass industrySecondClass) {
        this.industrySecondClasses.remove(industrySecondClass);
        industrySecondClass.setIndustryFirstClass(null);
        return this;
    }

    public void setIndustrySecondClasses(Set<IndustrySecondClass> industrySecondClasses) {
        this.industrySecondClasses = industrySecondClasses;
    }

    public IndustryAll getIndustryAll() {
        return industryAll;
    }

    public IndustryFirstClass industryAll(IndustryAll industryAll) {
        this.industryAll = industryAll;
        return this;
    }

    public void setIndustryAll(IndustryAll industryAll) {
        this.industryAll = industryAll;
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
        IndustryFirstClass industryFirstClass = (IndustryFirstClass) o;
        if (industryFirstClass.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), industryFirstClass.getId());
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
