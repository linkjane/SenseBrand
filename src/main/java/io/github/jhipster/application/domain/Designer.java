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
 * A Designer.
 */
@Entity
@Table(name = "designer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "designer")
public class Designer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "zn_name", nullable = false)
    private String znName;


    @Column(name = "en_name")
    private String enName;


    @Column(name = "profile_back_file")
    private String profileBackFile;

    @Column(name = "profile_back_file_url")
    private String profileBackFileUrl;

    @Column(name = "profile_back_file_content_type")
    private String profileBackFileContentType;

    @Column(name = "profile_thumbnail_file")
    private String profileThumbnailFile;

    @Column(name = "profile_thumbnail_file_url")
    private String profileThumbnailFileUrl;

    @Column(name = "profile_thumbnail_file_content_type")
    private String profileThumbnailFileContentType;

    @Column(name = "position")
    private String position;


    @Lob
    @Column(name = "introduction")
    private String introduction;


    @Column(name = "is_show")
    private Boolean isShow;


    @OneToMany(mappedBy = "designer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DesignerShowImg> designerShowImgs = new HashSet<>();

    @OneToMany(mappedBy = "designer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DesignerSentiment> designerSentiments = new HashSet<>();

    @OneToMany(mappedBy = "designer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DesignerAward> designerAwards = new HashSet<>();

    @OneToMany(mappedBy = "designer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DesignerIdeaMedia> designerIdeaMedias = new HashSet<>();

    @OneToOne(mappedBy = "designer")
    @JsonIgnore
    private DesignerIdeaDetails designerIdeaDetails;

    @OneToOne(mappedBy = "designer")
    @JsonIgnore
    private DesignerShow designerShow;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZnName() {
        return znName;
    }

    public Designer znName(String znName) {
        this.znName = znName;
        return this;
    }

    public void setZnName(String znName) {
        this.znName = znName;
    }

    public String getEnName() {
        return enName;
    }

    public Designer enName(String enName) {
        this.enName = enName;
        return this;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getProfileBackFile() {
        return profileBackFile;
    }

    public Designer profileBackFile(String profileBackFile) {
        this.profileBackFile = profileBackFile;
        return this;
    }

    public void setProfileBackFile(String profileBackFile) {
        this.profileBackFile = profileBackFile;
    }

    public String getProfileBackFileContentType() {
        return profileBackFileContentType;
    }


    public String getProfileBackFileUrl() {
        return profileBackFileUrl;
    }



    public Designer profileBackFileContentType(String profileBackFileContentType) {
        this.profileBackFileContentType = profileBackFileContentType;
        return this;
    }

    public void setProfileBackFileContentType(String profileBackFileContentType) {
        this.profileBackFileContentType = profileBackFileContentType;
    }

    public void setProfileBackFileUrl(String profileBackFileUrl) {
        this.profileBackFileUrl = profileBackFileUrl;
    }


    public String getProfileThumbnailFile() {
        return profileThumbnailFile;
    }

    public Designer profileThumbnailFile(String profileThumbnailFile) {
        this.profileThumbnailFile = profileThumbnailFile;
        return this;
    }

    public void setProfileThumbnailFile(String profileThumbnailFile) {
        this.profileThumbnailFile = profileThumbnailFile;
    }

    public String getProfileThumbnailFileContentType() {
        return profileThumbnailFileContentType;
    }


    public String getProfileThumbnailFileUrl() {
        return profileThumbnailFileUrl;
    }



    public Designer profileThumbnailFileContentType(String profileThumbnailFileContentType) {
        this.profileThumbnailFileContentType = profileThumbnailFileContentType;
        return this;
    }

    public void setProfileThumbnailFileContentType(String profileThumbnailFileContentType) {
        this.profileThumbnailFileContentType = profileThumbnailFileContentType;
    }

    public void setProfileThumbnailFileUrl(String profileThumbnailFileUrl) {
        this.profileThumbnailFileUrl = profileThumbnailFileUrl;
    }


    public String getPosition() {
        return position;
    }

    public Designer position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getIntroduction() {
        return introduction;
    }

    public Designer introduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Boolean isIsShow() {
        return isShow;
    }

    public Designer isShow(Boolean isShow) {
        this.isShow = isShow;
        return this;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public Set<DesignerShowImg> getDesignerShowImgs() {
        return designerShowImgs;
    }

    public Designer designerShowImgs(Set<DesignerShowImg> designerShowImgs) {
        this.designerShowImgs = designerShowImgs;
        return this;
    }

    public Designer addDesignerShowImg(DesignerShowImg designerShowImg) {
        this.designerShowImgs.add(designerShowImg);
        designerShowImg.setDesigner(this);
        return this;
    }

    public Designer removeDesignerShowImg(DesignerShowImg designerShowImg) {
        this.designerShowImgs.remove(designerShowImg);
        designerShowImg.setDesigner(null);
        return this;
    }

    public void setDesignerShowImgs(Set<DesignerShowImg> designerShowImgs) {
        this.designerShowImgs = designerShowImgs;
    }

    public Set<DesignerSentiment> getDesignerSentiments() {
        return designerSentiments;
    }

    public Designer designerSentiments(Set<DesignerSentiment> designerSentiments) {
        this.designerSentiments = designerSentiments;
        return this;
    }

    public Designer addDesignerSentiment(DesignerSentiment designerSentiment) {
        this.designerSentiments.add(designerSentiment);
        designerSentiment.setDesigner(this);
        return this;
    }

    public Designer removeDesignerSentiment(DesignerSentiment designerSentiment) {
        this.designerSentiments.remove(designerSentiment);
        designerSentiment.setDesigner(null);
        return this;
    }

    public void setDesignerSentiments(Set<DesignerSentiment> designerSentiments) {
        this.designerSentiments = designerSentiments;
    }

    public Set<DesignerAward> getDesignerAwards() {
        return designerAwards;
    }

    public Designer designerAwards(Set<DesignerAward> designerAwards) {
        this.designerAwards = designerAwards;
        return this;
    }

    public Designer addDesignerAward(DesignerAward designerAward) {
        this.designerAwards.add(designerAward);
        designerAward.setDesigner(this);
        return this;
    }

    public Designer removeDesignerAward(DesignerAward designerAward) {
        this.designerAwards.remove(designerAward);
        designerAward.setDesigner(null);
        return this;
    }

    public void setDesignerAwards(Set<DesignerAward> designerAwards) {
        this.designerAwards = designerAwards;
    }

    public Set<DesignerIdeaMedia> getDesignerIdeaMedias() {
        return designerIdeaMedias;
    }

    public Designer designerIdeaMedias(Set<DesignerIdeaMedia> designerIdeaMedias) {
        this.designerIdeaMedias = designerIdeaMedias;
        return this;
    }

    public Designer addDesignerIdeaMedia(DesignerIdeaMedia designerIdeaMedia) {
        this.designerIdeaMedias.add(designerIdeaMedia);
        designerIdeaMedia.setDesigner(this);
        return this;
    }

    public Designer removeDesignerIdeaMedia(DesignerIdeaMedia designerIdeaMedia) {
        this.designerIdeaMedias.remove(designerIdeaMedia);
        designerIdeaMedia.setDesigner(null);
        return this;
    }

    public void setDesignerIdeaMedias(Set<DesignerIdeaMedia> designerIdeaMedias) {
        this.designerIdeaMedias = designerIdeaMedias;
    }

    public DesignerIdeaDetails getDesignerIdeaDetails() {
        return designerIdeaDetails;
    }

    public Designer designerIdeaDetails(DesignerIdeaDetails designerIdeaDetails) {
        this.designerIdeaDetails = designerIdeaDetails;
        return this;
    }

    public void setDesignerIdeaDetails(DesignerIdeaDetails designerIdeaDetails) {
        this.designerIdeaDetails = designerIdeaDetails;
    }

    public DesignerShow getDesignerShow() {
        return designerShow;
    }

    public Designer designerShow(DesignerShow designerShow) {
        this.designerShow = designerShow;
        return this;
    }

    public void setDesignerShow(DesignerShow designerShow) {
        this.designerShow = designerShow;
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
        Designer designer = (Designer) o;
        if (designer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), designer.getId());
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
