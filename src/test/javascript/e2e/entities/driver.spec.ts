import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Driver e2e test', () => {

    let navBarPage: NavBarPage;
    let driverDialogPage: DriverDialogPage;
    let driverComponentsPage: DriverComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Drivers', () => {
        navBarPage.goToEntity('driver');
        driverComponentsPage = new DriverComponentsPage();
        expect(driverComponentsPage.getTitle())
            .toMatch(/senseBrandApp.driver.home.title/);

    });

    it('should load create Driver dialog', () => {
        driverComponentsPage.clickOnCreateButton();
        driverDialogPage = new DriverDialogPage();
        expect(driverDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.driver.home.createOrEditLabel/);
        driverDialogPage.close();
    });

    it('should create and save Drivers', () => {
        driverComponentsPage.clickOnCreateButton();
        driverDialogPage.setNameInput('name');
        expect(driverDialogPage.getNameInput()).toMatch('name');
        driverDialogPage.save();
        expect(driverDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class DriverComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-driver div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class DriverDialogPage {
    modalTitle = element(by.css('h4#myDriverLabel'));
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
