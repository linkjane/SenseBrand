import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('BrandRank e2e test', () => {

    let navBarPage: NavBarPage;
    let brandRankDialogPage: BrandRankDialogPage;
    let brandRankComponentsPage: BrandRankComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load BrandRanks', () => {
        navBarPage.goToEntity('brand-rank');
        brandRankComponentsPage = new BrandRankComponentsPage();
        expect(brandRankComponentsPage.getTitle())
            .toMatch(/senseBrandApp.brandRank.home.title/);

    });

    it('should load create BrandRank dialog', () => {
        brandRankComponentsPage.clickOnCreateButton();
        brandRankDialogPage = new BrandRankDialogPage();
        expect(brandRankDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.brandRank.home.createOrEditLabel/);
        brandRankDialogPage.close();
    });

    it('should create and save BrandRanks', () => {
        brandRankComponentsPage.clickOnCreateButton();
        brandRankDialogPage.setNameInput('name');
        expect(brandRankDialogPage.getNameInput()).toMatch('name');
        brandRankDialogPage.save();
        expect(brandRankDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class BrandRankComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-brand-rank div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class BrandRankDialogPage {
    modalTitle = element(by.css('h4#myBrandRankLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
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
