import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('BrandSubDetails e2e test', () => {

    let navBarPage: NavBarPage;
    let brandSubDetailsDialogPage: BrandSubDetailsDialogPage;
    let brandSubDetailsComponentsPage: BrandSubDetailsComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load BrandSubDetails', () => {
        navBarPage.goToEntity('brand-sub-details');
        brandSubDetailsComponentsPage = new BrandSubDetailsComponentsPage();
        expect(brandSubDetailsComponentsPage.getTitle())
            .toMatch(/senseBrandApp.brandSubDetails.home.title/);

    });

    it('should load create BrandSubDetails dialog', () => {
        brandSubDetailsComponentsPage.clickOnCreateButton();
        brandSubDetailsDialogPage = new BrandSubDetailsDialogPage();
        expect(brandSubDetailsDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.brandSubDetails.home.createOrEditLabel/);
        brandSubDetailsDialogPage.close();
    });

    it('should create and save BrandSubDetails', () => {
        brandSubDetailsComponentsPage.clickOnCreateButton();
        brandSubDetailsDialogPage.setTitleInput('title');
        expect(brandSubDetailsDialogPage.getTitleInput()).toMatch('title');
        brandSubDetailsDialogPage.setIntroductionInput('introduction');
        expect(brandSubDetailsDialogPage.getIntroductionInput()).toMatch('introduction');
        brandSubDetailsDialogPage.setBannerImgFileInput('bannerImgFile');
        expect(brandSubDetailsDialogPage.getBannerImgFileInput()).toMatch('bannerImgFile');
        brandSubDetailsDialogPage.setContentInput('content');
        expect(brandSubDetailsDialogPage.getContentInput()).toMatch('content');
        brandSubDetailsDialogPage.setCreatedTimeInput('2000-12-31');
        expect(brandSubDetailsDialogPage.getCreatedTimeInput()).toMatch('2000-12-31');
        brandSubDetailsDialogPage.brandSelectLastOption();
        brandSubDetailsDialogPage.brandSubSelectLastOption();
        brandSubDetailsDialogPage.save();
        expect(brandSubDetailsDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class BrandSubDetailsComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-brand-sub-details div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class BrandSubDetailsDialogPage {
    modalTitle = element(by.css('h4#myBrandSubDetailsLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));
    introductionInput = element(by.css('textarea#field_introduction'));
    bannerImgFileInput = element(by.css('input#field_bannerImgFile'));
    contentInput = element(by.css('textarea#field_content'));
    createdTimeInput = element(by.css('input#field_createdTime'));
    brandSelect = element(by.css('select#field_brand'));
    brandSubSelect = element(by.css('select#field_brandSub'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTitleInput = function(title) {
        this.titleInput.sendKeys(title);
    };

    getTitleInput = function() {
        return this.titleInput.getAttribute('value');
    };

    setIntroductionInput = function(introduction) {
        this.introductionInput.sendKeys(introduction);
    };

    getIntroductionInput = function() {
        return this.introductionInput.getAttribute('value');
    };

    setBannerImgFileInput = function(bannerImgFile) {
        this.bannerImgFileInput.sendKeys(bannerImgFile);
    };

    getBannerImgFileInput = function() {
        return this.bannerImgFileInput.getAttribute('value');
    };

    setContentInput = function(content) {
        this.contentInput.sendKeys(content);
    };

    getContentInput = function() {
        return this.contentInput.getAttribute('value');
    };

    setCreatedTimeInput = function(createdTime) {
        this.createdTimeInput.sendKeys(createdTime);
    };

    getCreatedTimeInput = function() {
        return this.createdTimeInput.getAttribute('value');
    };

    brandSelectLastOption = function() {
        this.brandSelect.all(by.tagName('option')).last().click();
    };

    brandSelectOption = function(option) {
        this.brandSelect.sendKeys(option);
    };

    getBrandSelect = function() {
        return this.brandSelect;
    };

    getBrandSelectedOption = function() {
        return this.brandSelect.element(by.css('option:checked')).getText();
    };

    brandSubSelectLastOption = function() {
        this.brandSubSelect.all(by.tagName('option')).last().click();
    };

    brandSubSelectOption = function(option) {
        this.brandSubSelect.sendKeys(option);
    };

    getBrandSubSelect = function() {
        return this.brandSubSelect;
    };

    getBrandSubSelectedOption = function() {
        return this.brandSubSelect.element(by.css('option:checked')).getText();
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
