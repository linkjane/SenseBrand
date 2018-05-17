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
 * A Brand.
 */
@Entity
@Table(name = "brand")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "brand")
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;


    @Column(name = "banner_img_file")
    private String bannerImgFile;

    @Column(name = "banner_img_file_url")
    private String bannerImgFileUrl;

    @Column(name = "banner_img_file_content_type")
    private String bannerImgFileContentType;

    @Column(name = "profile_img_file")
    private String profileImgFile;

    @Column(name = "profile_img_file_url")
    private String profileImgFileUrl;

    @Column(name = "profile_img_file_content_type")
    private String profileImgFileContentType;

    @NotNull
    @Lob
    @Column(name = "introduction", nullable = false)
    private String introduction;


    @Column(name = "logo")
    private String logo;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @Column(name = "establish_time")
    private String establishTime;


    @Column(name = "cradle")
    private String cradle;


    @Column(name = "chairman")
    private String chairman;


    @Column(name = "phone_number")
    private String phoneNumber;


    @Column(name = "official_website")
    private String officialWebsite;


    @Column(name = "ad_phrase")
    private String adPhrase;


    @OneToMany(mappedBy = "brand")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BrandSubDetails> brandSubDetails = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "brand_brand_rank",
               joinColumns = @JoinColumn(name="brands_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="brand_ranks_id", referencedColumnName="id"))
    private Set<BrandRank> brandRanks = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "brand_brand_region",
               joinColumns = @JoinColumn(name="brands_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="brand_regions_id", referencedColumnName="id"))
    private Set<BrandRegion> brandRegions = new HashSet<>();

    @ManyToOne
    private IndustrySecondClass industrySecondClass;

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

    public Brand title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBannerImgFile() {
        return bannerImgFile;
    }

    public Brand bannerImgFile(String bannerImgFile) {
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



    public Brand bannerImgFileContentType(String bannerImgFileContentType) {
        this.bannerImgFileContentType = bannerImgFileContentType;
        return this;
    }

    public void setBannerImgFileContentType(String bannerImgFileContentType) {
        this.bannerImgFileContentType = bannerImgFileContentType;
    }

    public void setBannerImgFileUrl(String bannerImgFileUrl) {
        this.bannerImgFileUrl = bannerImgFileUrl;
    }


    public String getProfileImgFile() {
        return profileImgFile;
    }

    public Brand profileImgFile(String profileImgFile) {
        this.profileImgFile = profileImgFile;
        return this;
    }

    public void setProfileImgFile(String profileImgFile) {
        this.profileImgFile = profileImgFile;
    }

    public String getProfileImgFileContentType() {
        return profileImgFileContentType;
    }


    public String getProfileImgFileUrl() {
        return profileImgFileUrl;
    }



    public Brand profileImgFileContentType(String profileImgFileContentType) {
        this.profileImgFileContentType = profileImgFileContentType;
        return this;
    }

    public void setProfileImgFileContentType(String profileImgFileContentType) {
        this.profileImgFileContentType = profileImgFileContentType;
    }

    public void setProfileImgFileUrl(String profileImgFileUrl) {
        this.profileImgFileUrl = profileImgFileUrl;
    }


    public String getIntroduction() {
        return introduction;
    }

    public Brand introduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getLogo() {
        return logo;
    }

    public Brand logo(String logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }


    public String getLogoUrl() {
        return logoUrl;
    }



    public Brand logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }


    public String getEstablishTime() {
        return establishTime;
    }

    public Brand establishTime(String establishTime) {
        this.establishTime = establishTime;
        return this;
    }

    public void setEstablishTime(String establishTime) {
        this.establishTime = establishTime;
    }

    public String getCradle() {
        return cradle;
    }

    public Brand cradle(String cradle) {
        this.cradle = cradle;
        return this;
    }

    public void setCradle(String cradle) {
        this.cradle = cradle;
    }

    public String getChairman() {
        return chairman;
    }

    public Brand chairman(String chairman) {
        this.chairman = chairman;
        return this;
    }

    public void setChairman(String chairman) {
        this.chairman = chairman;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Brand phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOfficialWebsite() {
        return officialWebsite;
    }

    public Brand officialWebsite(String officialWebsite) {
        this.officialWebsite = officialWebsite;
        return this;
    }

    public void setOfficialWebsite(String officialWebsite) {
        this.officialWebsite = officialWebsite;
    }

    public String getAdPhrase() {
        return adPhrase;
    }

    public Brand adPhrase(String adPhrase) {
        this.adPhrase = adPhrase;
        return this;
    }

    public void setAdPhrase(String adPhrase) {
        this.adPhrase = adPhrase;
    }

    public Set<BrandSubDetails> getBrandSubDetails() {
        return brandSubDetails;
    }

    public Brand brandSubDetails(Set<BrandSubDetails> brandSubDetails) {
        this.brandSubDetails = brandSubDetails;
        return this;
    }

    public Brand addBrandSubDetails(BrandSubDetails brandSubDetails) {
        this.brandSubDetails.add(brandSubDetails);
        brandSubDetails.setBrand(this);
        return this;
    }

    public Brand removeBrandSubDetails(BrandSubDetails brandSubDetails) {
        this.brandSubDetails.remove(brandSubDetails);
        brandSubDetails.setBrand(null);
        return this;
    }

    public void setBrandSubDetails(Set<BrandSubDetails> brandSubDetails) {
        this.brandSubDetails = brandSubDetails;
    }

    public Set<BrandRank> getBrandRanks() {
        return brandRanks;
    }

    public Brand brandRanks(Set<BrandRank> brandRanks) {
        this.brandRanks = brandRanks;
        return this;
    }

    public Brand addBrandRank(BrandRank brandRank) {
        this.brandRanks.add(brandRank);
        brandRank.getBrands().add(this);
        return this;
    }

    public Brand removeBrandRank(BrandRank brandRank) {
        this.brandRanks.remove(brandRank);
        brandRank.getBrands().remove(this);
        return this;
    }

    public void setBrandRanks(Set<BrandRank> brandRanks) {
        this.brandRanks = brandRanks;
    }

    public Set<BrandRegion> getBrandRegions() {
        return brandRegions;
    }

    public Brand brandRegions(Set<BrandRegion> brandRegions) {
        this.brandRegions = brandRegions;
        return this;
    }

    public Brand addBrandRegion(BrandRegion brandRegion) {
        this.brandRegions.add(brandRegion);
        brandRegion.getBrands().add(this);
        return this;
    }

    public Brand removeBrandRegion(BrandRegion brandRegion) {
        this.brandRegions.remove(brandRegion);
        brandRegion.getBrands().remove(this);
        return this;
    }

    public void setBrandRegions(Set<BrandRegion> brandRegions) {
        this.brandRegions = brandRegions;
    }

    public IndustrySecondClass getIndustrySecondClass() {
        return industrySecondClass;
    }

    public Brand industrySecondClass(IndustrySecondClass industrySecondClass) {
        this.industrySecondClass = industrySecondClass;
        return this;
    }

    public void setIndustrySecondClass(IndustrySecondClass industrySecondClass) {
        this.industrySecondClass = industrySecondClass;
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
        Brand brand = (Brand) o;
        if (brand.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), brand.getId());
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
