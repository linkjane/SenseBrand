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
    private Set<DesignerShowImg> showImgs = new HashSet<>();

    @OneToMany(mappedBy = "designer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DesignerSentiment> sentiments = new HashSet<>();

    @OneToMany(mappedBy = "designer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DesignerAward> awards = new HashSet<>();

    @OneToMany(mappedBy = "designer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DesignerIdeaMedia> ideaMedias = new HashSet<>();

    @OneToOne(mappedBy = "designer")
    @JsonIgnore
    private DesignerIdeaDetails ideaDetails;

    @OneToOne(mappedBy = "designer")
    @JsonIgnore
    private DesignerShow show;

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

    public Set<DesignerShowImg> getShowImgs() {
        return showImgs;
    }

    public Designer showImgs(Set<DesignerShowImg> designerShowImgs) {
        this.showImgs = designerShowImgs;
        return this;
    }

    public Designer addShowImg(DesignerShowImg designerShowImg) {
        this.showImgs.add(designerShowImg);
        designerShowImg.setDesigner(this);
        return this;
    }

    public Designer removeShowImg(DesignerShowImg designerShowImg) {
        this.showImgs.remove(designerShowImg);
        designerShowImg.setDesigner(null);
        return this;
    }

    public void setShowImgs(Set<DesignerShowImg> designerShowImgs) {
        this.showImgs = designerShowImgs;
    }

    public Set<DesignerSentiment> getSentiments() {
        return sentiments;
    }

    public Designer sentiments(Set<DesignerSentiment> designerSentiments) {
        this.sentiments = designerSentiments;
        return this;
    }

    public Designer addSentiment(DesignerSentiment designerSentiment) {
        this.sentiments.add(designerSentiment);
        designerSentiment.setDesigner(this);
        return this;
    }

    public Designer removeSentiment(DesignerSentiment designerSentiment) {
        this.sentiments.remove(designerSentiment);
        designerSentiment.setDesigner(null);
        return this;
    }

    public void setSentiments(Set<DesignerSentiment> designerSentiments) {
        this.sentiments = designerSentiments;
    }

    public Set<DesignerAward> getAwards() {
        return awards;
    }

    public Designer awards(Set<DesignerAward> designerAwards) {
        this.awards = designerAwards;
        return this;
    }

    public Designer addAward(DesignerAward designerAward) {
        this.awards.add(designerAward);
        designerAward.setDesigner(this);
        return this;
    }

    public Designer removeAward(DesignerAward designerAward) {
        this.awards.remove(designerAward);
        designerAward.setDesigner(null);
        return this;
    }

    public void setAwards(Set<DesignerAward> designerAwards) {
        this.awards = designerAwards;
    }

    public Set<DesignerIdeaMedia> getIdeaMedias() {
        return ideaMedias;
    }

    public Designer ideaMedias(Set<DesignerIdeaMedia> designerIdeaMedias) {
        this.ideaMedias = designerIdeaMedias;
        return this;
    }

    public Designer addIdeaMedia(DesignerIdeaMedia designerIdeaMedia) {
        this.ideaMedias.add(designerIdeaMedia);
        designerIdeaMedia.setDesigner(this);
        return this;
    }

    public Designer removeIdeaMedia(DesignerIdeaMedia designerIdeaMedia) {
        this.ideaMedias.remove(designerIdeaMedia);
        designerIdeaMedia.setDesigner(null);
        return this;
    }

    public void setIdeaMedias(Set<DesignerIdeaMedia> designerIdeaMedias) {
        this.ideaMedias = designerIdeaMedias;
    }

    public DesignerIdeaDetails getIdeaDetails() {
        return ideaDetails;
    }

    public Designer ideaDetails(DesignerIdeaDetails designerIdeaDetails) {
        this.ideaDetails = designerIdeaDetails;
        return this;
    }

    public void setIdeaDetails(DesignerIdeaDetails designerIdeaDetails) {
        this.ideaDetails = designerIdeaDetails;
    }

    public DesignerShow getShow() {
        return show;
    }

    public Designer show(DesignerShow designerShow) {
        this.show = designerShow;
        return this;
    }

    public void setShow(DesignerShow designerShow) {
        this.show = designerShow;
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
