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
 * A IndustryType.
 */
@Entity
@Table(name = "industry_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "industrytype")
public class IndustryType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "small_title")
    private String smallTitle;


    @OneToMany(mappedBy = "industryType")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IndustryTypeName> industryTypeNames = new HashSet<>();

    @ManyToOne
    private Industry industry;

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

    public IndustryType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSmallTitle() {
        return smallTitle;
    }

    public IndustryType smallTitle(String smallTitle) {
        this.smallTitle = smallTitle;
        return this;
    }

    public void setSmallTitle(String smallTitle) {
        this.smallTitle = smallTitle;
    }

    public Set<IndustryTypeName> getIndustryTypeNames() {
        return industryTypeNames;
    }

    public IndustryType industryTypeNames(Set<IndustryTypeName> industryTypeNames) {
        this.industryTypeNames = industryTypeNames;
        return this;
    }

    public IndustryType addIndustryTypeName(IndustryTypeName industryTypeName) {
        this.industryTypeNames.add(industryTypeName);
        industryTypeName.setIndustryType(this);
        return this;
    }

    public IndustryType removeIndustryTypeName(IndustryTypeName industryTypeName) {
        this.industryTypeNames.remove(industryTypeName);
        industryTypeName.setIndustryType(null);
        return this;
    }

    public void setIndustryTypeNames(Set<IndustryTypeName> industryTypeNames) {
        this.industryTypeNames = industryTypeNames;
    }

    public Industry getIndustry() {
        return industry;
    }

    public IndustryType industry(Industry industry) {
        this.industry = industry;
        return this;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
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
        IndustryType industryType = (IndustryType) o;
        if (industryType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), industryType.getId());
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
