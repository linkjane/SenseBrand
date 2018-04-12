import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Owner e2e test', () => {

    let navBarPage: NavBarPage;
    let ownerDialogPage: OwnerDialogPage;
    let ownerComponentsPage: OwnerComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Owners', () => {
        navBarPage.goToEntity('owner');
        ownerComponentsPage = new OwnerComponentsPage();
        expect(ownerComponentsPage.getTitle())
            .toMatch(/senseBrandApp.owner.home.title/);

    });

    it('should load create Owner dialog', () => {
        ownerComponentsPage.clickOnCreateButton();
        ownerDialogPage = new OwnerDialogPage();
        expect(ownerDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.owner.home.createOrEditLabel/);
        ownerDialogPage.close();
    });

    it('should create and save Owners', () => {
        ownerComponentsPage.clickOnCreateButton();
        ownerDialogPage.setNameInput('name');
        expect(ownerDialogPage.getNameInput()).toMatch('name');
        ownerDialogPage.setAgeInput('5');
        expect(ownerDialogPage.getAgeInput()).toMatch('5');
        ownerDialogPage.save();
        expect(ownerDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class OwnerComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-owner div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class OwnerDialogPage {
    modalTitle = element(by.css('h4#myOwnerLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    ageInput = element(by.css('input#field_age'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    };

    setAgeInput = function(age) {
        this.ageInput.sendKeys(age);
    };

    getAgeInput = function() {
        return this.ageInput.getAttribute('value');
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
