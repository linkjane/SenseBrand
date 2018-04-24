import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('BrandSub e2e test', () => {

    let navBarPage: NavBarPage;
    let brandSubDialogPage: BrandSubDialogPage;
    let brandSubComponentsPage: BrandSubComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load BrandSubs', () => {
        navBarPage.goToEntity('brand-sub');
        brandSubComponentsPage = new BrandSubComponentsPage();
        expect(brandSubComponentsPage.getTitle())
            .toMatch(/senseBrandApp.brandSub.home.title/);

    });

    it('should load create BrandSub dialog', () => {
        brandSubComponentsPage.clickOnCreateButton();
        brandSubDialogPage = new BrandSubDialogPage();
        expect(brandSubDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.brandSub.home.createOrEditLabel/);
        brandSubDialogPage.close();
    });

    it('should create and save BrandSubs', () => {
        brandSubComponentsPage.clickOnCreateButton();
        brandSubDialogPage.setTitleInput('title');
        expect(brandSubDialogPage.getTitleInput()).toMatch('title');
        brandSubDialogPage.setIntroductionInput('introduction');
        expect(brandSubDialogPage.getIntroductionInput()).toMatch('introduction');
        brandSubDialogPage.brandSelectLastOption();
        brandSubDialogPage.save();
        expect(brandSubDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class BrandSubComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-brand-sub div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class BrandSubDialogPage {
    modalTitle = element(by.css('h4#myBrandSubLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));
    introductionInput = element(by.css('textarea#field_introduction'));
    brandSelect = element(by.css('select#field_brand'));

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
