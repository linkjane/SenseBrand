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
 * A BrandSubDetails.
 */
@Entity
@Table(name = "brand_sub_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "brandsubdetails")
public class BrandSubDetails implements Serializable {

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


    @Column(name = "banner_img_file")
    private String bannerImgFile;

    @Column(name = "banner_img_file_url")
    private String bannerImgFileUrl;

    @Column(name = "banner_img_file_content_type")
    private String bannerImgFileContentType;

    @Lob
    @Column(name = "content")
    private String content;


    @Column(name = "created_time")
    private LocalDate createdTime;


    @ManyToOne
    private Brand brand;

    @ManyToOne
    private BrandSub brandSub;

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

    public BrandSubDetails title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public BrandSubDetails introduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getBannerImgFile() {
        return bannerImgFile;
    }

    public BrandSubDetails bannerImgFile(String bannerImgFile) {
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



    public BrandSubDetails bannerImgFileContentType(String bannerImgFileContentType) {
        this.bannerImgFileContentType = bannerImgFileContentType;
        return this;
    }

    public void setBannerImgFileContentType(String bannerImgFileContentType) {
        this.bannerImgFileContentType = bannerImgFileContentType;
    }

    public void setBannerImgFileUrl(String bannerImgFileUrl) {
        this.bannerImgFileUrl = bannerImgFileUrl;
    }


    public String getContent() {
        return content;
    }

    public BrandSubDetails content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreatedTime() {
        return createdTime;
    }

    public BrandSubDetails createdTime(LocalDate createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(LocalDate createdTime) {
        this.createdTime = createdTime;
    }

    public Brand getBrand() {
        return brand;
    }

    public BrandSubDetails brand(Brand brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public BrandSub getBrandSub() {
        return brandSub;
    }

    public BrandSubDetails brandSub(BrandSub brandSub) {
        this.brandSub = brandSub;
        return this;
    }

    public void setBrandSub(BrandSub brandSub) {
        this.brandSub = brandSub;
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
        BrandSubDetails brandSubDetails = (BrandSubDetails) o;
        if (brandSubDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), brandSubDetails.getId());
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
