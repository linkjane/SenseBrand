import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Brand e2e test', () => {

    let navBarPage: NavBarPage;
    let brandDialogPage: BrandDialogPage;
    let brandComponentsPage: BrandComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Brands', () => {
        navBarPage.goToEntity('brand');
        brandComponentsPage = new BrandComponentsPage();
        expect(brandComponentsPage.getTitle())
            .toMatch(/senseBrandApp.brand.home.title/);

    });

    it('should load create Brand dialog', () => {
        brandComponentsPage.clickOnCreateButton();
        brandDialogPage = new BrandDialogPage();
        expect(brandDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.brand.home.createOrEditLabel/);
        brandDialogPage.close();
    });

    it('should create and save Brands', () => {
        brandComponentsPage.clickOnCreateButton();
        brandDialogPage.setTitleInput('title');
        expect(brandDialogPage.getTitleInput()).toMatch('title');
        brandDialogPage.setBannerImgFileInput('bannerImgFile');
        expect(brandDialogPage.getBannerImgFileInput()).toMatch('bannerImgFile');
        brandDialogPage.setProfileImgFileInput('profileImgFile');
        expect(brandDialogPage.getProfileImgFileInput()).toMatch('profileImgFile');
        brandDialogPage.setIntroductionInput('introduction');
        expect(brandDialogPage.getIntroductionInput()).toMatch('introduction');
        brandDialogPage.setLogoInput('logo');
        expect(brandDialogPage.getLogoInput()).toMatch('logo');
        brandDialogPage.setEstablishTimeInput('establishTime');
        expect(brandDialogPage.getEstablishTimeInput()).toMatch('establishTime');
        brandDialogPage.setCradleInput('cradle');
        expect(brandDialogPage.getCradleInput()).toMatch('cradle');
        brandDialogPage.setChairmanInput('chairman');
        expect(brandDialogPage.getChairmanInput()).toMatch('chairman');
        brandDialogPage.setPhoneNumberInput('phoneNumber');
        expect(brandDialogPage.getPhoneNumberInput()).toMatch('phoneNumber');
        brandDialogPage.setOfficialWebsiteInput('officialWebsite');
        expect(brandDialogPage.getOfficialWebsiteInput()).toMatch('officialWebsite');
        brandDialogPage.setAdPhraseInput('adPhrase');
        expect(brandDialogPage.getAdPhraseInput()).toMatch('adPhrase');
        // brandDialogPage.brandRankSelectLastOption();
        // brandDialogPage.brandRegionSelectLastOption();
        brandDialogPage.industrySecondClassSelectLastOption();
        brandDialogPage.save();
        expect(brandDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class BrandComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-brand div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class BrandDialogPage {
    modalTitle = element(by.css('h4#myBrandLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));
    bannerImgFileInput = element(by.css('input#field_bannerImgFile'));
    profileImgFileInput = element(by.css('input#field_profileImgFile'));
    introductionInput = element(by.css('textarea#field_introduction'));
    logoInput = element(by.css('input#field_logo'));
    establishTimeInput = element(by.css('input#field_establishTime'));
    cradleInput = element(by.css('input#field_cradle'));
    chairmanInput = element(by.css('input#field_chairman'));
    phoneNumberInput = element(by.css('input#field_phoneNumber'));
    officialWebsiteInput = element(by.css('input#field_officialWebsite'));
    adPhraseInput = element(by.css('input#field_adPhrase'));
    brandRankSelect = element(by.css('select#field_brandRank'));
    brandRegionSelect = element(by.css('select#field_brandRegion'));
    industrySecondClassSelect = element(by.css('select#field_industrySecondClass'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTitleInput = function(title) {
        this.titleInput.sendKeys(title);
    };

    getTitleInput = function() {
        return this.titleInput.getAttribute('value');
    };

    setBannerImgFileInput = function(bannerImgFile) {
        this.bannerImgFileInput.sendKeys(bannerImgFile);
    };

    getBannerImgFileInput = function() {
        return this.bannerImgFileInput.getAttribute('value');
    };

    setProfileImgFileInput = function(profileImgFile) {
        this.profileImgFileInput.sendKeys(profileImgFile);
    };

    getProfileImgFileInput = function() {
        return this.profileImgFileInput.getAttribute('value');
    };

    setIntroductionInput = function(introduction) {
        this.introductionInput.sendKeys(introduction);
    };

    getIntroductionInput = function() {
        return this.introductionInput.getAttribute('value');
    };

    setLogoInput = function(logo) {
        this.logoInput.sendKeys(logo);
    };

    getLogoInput = function() {
        return this.logoInput.getAttribute('value');
    };

    setEstablishTimeInput = function(establishTime) {
        this.establishTimeInput.sendKeys(establishTime);
    };

    getEstablishTimeInput = function() {
        return this.establishTimeInput.getAttribute('value');
    };

    setCradleInput = function(cradle) {
        this.cradleInput.sendKeys(cradle);
    };

    getCradleInput = function() {
        return this.cradleInput.getAttribute('value');
    };

    setChairmanInput = function(chairman) {
        this.chairmanInput.sendKeys(chairman);
    };

    getChairmanInput = function() {
        return this.chairmanInput.getAttribute('value');
    };

    setPhoneNumberInput = function(phoneNumber) {
        this.phoneNumberInput.sendKeys(phoneNumber);
    };

    getPhoneNumberInput = function() {
        return this.phoneNumberInput.getAttribute('value');
    };

    setOfficialWebsiteInput = function(officialWebsite) {
        this.officialWebsiteInput.sendKeys(officialWebsite);
    };

    getOfficialWebsiteInput = function() {
        return this.officialWebsiteInput.getAttribute('value');
    };

    setAdPhraseInput = function(adPhrase) {
        this.adPhraseInput.sendKeys(adPhrase);
    };

    getAdPhraseInput = function() {
        return this.adPhraseInput.getAttribute('value');
    };

    brandRankSelectLastOption = function() {
        this.brandRankSelect.all(by.tagName('option')).last().click();
    };

    brandRankSelectOption = function(option) {
        this.brandRankSelect.sendKeys(option);
    };

    getBrandRankSelect = function() {
        return this.brandRankSelect;
    };

    getBrandRankSelectedOption = function() {
        return this.brandRankSelect.element(by.css('option:checked')).getText();
    };

    brandRegionSelectLastOption = function() {
        this.brandRegionSelect.all(by.tagName('option')).last().click();
    };

    brandRegionSelectOption = function(option) {
        this.brandRegionSelect.sendKeys(option);
    };

    getBrandRegionSelect = function() {
        return this.brandRegionSelect;
    };

    getBrandRegionSelectedOption = function() {
        return this.brandRegionSelect.element(by.css('option:checked')).getText();
    };

    industrySecondClassSelectLastOption = function() {
        this.industrySecondClassSelect.all(by.tagName('option')).last().click();
    };

    industrySecondClassSelectOption = function(option) {
        this.industrySecondClassSelect.sendKeys(option);
    };

    getIndustrySecondClassSelect = function() {
        return this.industrySecondClassSelect;
    };

    getIndustrySecondClassSelectedOption = function() {
        return this.industrySecondClassSelect.element(by.css('option:checked')).getText();
    };

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
