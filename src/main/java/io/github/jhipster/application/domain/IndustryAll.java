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
 * A IndustryAll.
 */
@Entity
@Table(name = "industry_all")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "industryall")
public class IndustryAll implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;


    @OneToMany(mappedBy = "industryAll")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IndustryFirstClass> industryFirstClasses = new HashSet<>();

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

    public IndustryAll name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<IndustryFirstClass> getIndustryFirstClasses() {
        return industryFirstClasses;
    }

    public IndustryAll industryFirstClasses(Set<IndustryFirstClass> industryFirstClasses) {
        this.industryFirstClasses = industryFirstClasses;
        return this;
    }

    public IndustryAll addIndustryFirstClass(IndustryFirstClass industryFirstClass) {
        this.industryFirstClasses.add(industryFirstClass);
        industryFirstClass.setIndustryAll(this);
        return this;
    }

    public IndustryAll removeIndustryFirstClass(IndustryFirstClass industryFirstClass) {
        this.industryFirstClasses.remove(industryFirstClass);
        industryFirstClass.setIndustryAll(null);
        return this;
    }

    public void setIndustryFirstClasses(Set<IndustryFirstClass> industryFirstClasses) {
        this.industryFirstClasses = industryFirstClasses;
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
        IndustryAll industryAll = (IndustryAll) o;
        if (industryAll.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), industryAll.getId());
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
