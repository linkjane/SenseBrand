import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('BrandRegion e2e test', () => {

    let navBarPage: NavBarPage;
    let brandRegionDialogPage: BrandRegionDialogPage;
    let brandRegionComponentsPage: BrandRegionComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load BrandRegions', () => {
        navBarPage.goToEntity('brand-region');
        brandRegionComponentsPage = new BrandRegionComponentsPage();
        expect(brandRegionComponentsPage.getTitle())
            .toMatch(/senseBrandApp.brandRegion.home.title/);

    });

    it('should load create BrandRegion dialog', () => {
        brandRegionComponentsPage.clickOnCreateButton();
        brandRegionDialogPage = new BrandRegionDialogPage();
        expect(brandRegionDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.brandRegion.home.createOrEditLabel/);
        brandRegionDialogPage.close();
    });

    it('should create and save BrandRegions', () => {
        brandRegionComponentsPage.clickOnCreateButton();
        brandRegionDialogPage.setNameInput('name');
        expect(brandRegionDialogPage.getNameInput()).toMatch('name');
        brandRegionDialogPage.save();
        expect(brandRegionDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class BrandRegionComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-brand-region div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class BrandRegionDialogPage {
    modalTitle = element(by.css('h4#myBrandRegionLabel'));
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
