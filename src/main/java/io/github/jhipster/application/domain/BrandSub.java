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
 * A BrandSub.
 */
@Entity
@Table(name = "brand_sub")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "brandsub")
public class BrandSub implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;


    @Lob
    @Column(name = "introduction")
    private String introduction;


    @OneToMany(mappedBy = "brandSub")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BrandSubDetails> brandSubDetails = new HashSet<>();

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

    public BrandSub title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public BrandSub introduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Set<BrandSubDetails> getBrandSubDetails() {
        return brandSubDetails;
    }

    public BrandSub brandSubDetails(Set<BrandSubDetails> brandSubDetails) {
        this.brandSubDetails = brandSubDetails;
        return this;
    }

    public BrandSub addBrandSubDetails(BrandSubDetails brandSubDetails) {
        this.brandSubDetails.add(brandSubDetails);
        brandSubDetails.setBrandSub(this);
        return this;
    }

    public BrandSub removeBrandSubDetails(BrandSubDetails brandSubDetails) {
        this.brandSubDetails.remove(brandSubDetails);
        brandSubDetails.setBrandSub(null);
        return this;
    }

    public void setBrandSubDetails(Set<BrandSubDetails> brandSubDetails) {
        this.brandSubDetails = brandSubDetails;
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
        BrandSub brandSub = (BrandSub) o;
        if (brandSub.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), brandSub.getId());
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
