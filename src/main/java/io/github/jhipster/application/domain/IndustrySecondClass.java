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
 * A IndustrySecondClass.
 */
@Entity
@Table(name = "industry_second_class")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "industrysecondclass")
public class IndustrySecondClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;


    @OneToMany(mappedBy = "industrySecondClass")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Brand> brands = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private IndustryFirstClass industryFirstClass;

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

    public IndustrySecondClass name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Brand> getBrands() {
        return brands;
    }

    public IndustrySecondClass brands(Set<Brand> brands) {
        this.brands = brands;
        return this;
    }

    public IndustrySecondClass addBrand(Brand brand) {
        this.brands.add(brand);
        brand.setIndustrySecondClass(this);
        return this;
    }

    public IndustrySecondClass removeBrand(Brand brand) {
        this.brands.remove(brand);
        brand.setIndustrySecondClass(null);
        return this;
    }

    public void setBrands(Set<Brand> brands) {
        this.brands = brands;
    }

    public IndustryFirstClass getIndustryFirstClass() {
        return industryFirstClass;
    }

    public IndustrySecondClass industryFirstClass(IndustryFirstClass industryFirstClass) {
        this.industryFirstClass = industryFirstClass;
        return this;
    }

    public void setIndustryFirstClass(IndustryFirstClass industryFirstClass) {
        this.industryFirstClass = industryFirstClass;
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
        IndustrySecondClass industrySecondClass = (IndustrySecondClass) o;
        if (industrySecondClass.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), industrySecondClass.getId());
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
