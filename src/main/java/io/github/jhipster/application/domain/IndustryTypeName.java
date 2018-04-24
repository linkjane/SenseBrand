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
 * A IndustryTypeName.
 */
@Entity
@Table(name = "industry_type_name")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "industrytypename")
public class IndustryTypeName implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;


    @Lob
    @Column(name = "introduction")
    private String introduction;


    @Column(name = "icon_file")
    private String iconFile;

    @Column(name = "icon_file_url")
    private String iconFileUrl;

    @Column(name = "icon_file_content_type")
    private String iconFileContentType;

    @ManyToOne
    private IndustryType industryType;

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

    public IndustryTypeName name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public IndustryTypeName introduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getIconFile() {
        return iconFile;
    }

    public IndustryTypeName iconFile(String iconFile) {
        this.iconFile = iconFile;
        return this;
    }

    public void setIconFile(String iconFile) {
        this.iconFile = iconFile;
    }

    public String getIconFileContentType() {
        return iconFileContentType;
    }


    public String getIconFileUrl() {
        return iconFileUrl;
    }



    public IndustryTypeName iconFileContentType(String iconFileContentType) {
        this.iconFileContentType = iconFileContentType;
        return this;
    }

    public void setIconFileContentType(String iconFileContentType) {
        this.iconFileContentType = iconFileContentType;
    }

    public void setIconFileUrl(String iconFileUrl) {
        this.iconFileUrl = iconFileUrl;
    }


    public IndustryType getIndustryType() {
        return industryType;
    }

    public IndustryTypeName industryType(IndustryType industryType) {
        this.industryType = industryType;
        return this;
    }

    public void setIndustryType(IndustryType industryType) {
        this.industryType = industryType;
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
        IndustryTypeName industryTypeName = (IndustryTypeName) o;
        if (industryTypeName.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), industryTypeName.getId());
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
