import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Car e2e test', () => {

    let navBarPage: NavBarPage;
    let carDialogPage: CarDialogPage;
    let carComponentsPage: CarComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Cars', () => {
        navBarPage.goToEntity('car');
        carComponentsPage = new CarComponentsPage();
        expect(carComponentsPage.getTitle())
            .toMatch(/senseBrandApp.car.home.title/);

    });

    it('should load create Car dialog', () => {
        carComponentsPage.clickOnCreateButton();
        carDialogPage = new CarDialogPage();
        expect(carDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.car.home.createOrEditLabel/);
        carDialogPage.close();
    });

    it('should create and save Cars', () => {
        carComponentsPage.clickOnCreateButton();
        carDialogPage.setNameInput('name');
        expect(carDialogPage.getNameInput()).toMatch('name');
        carDialogPage.ownerSelectLastOption();
        // carDialogPage.driverSelectLastOption();
        carDialogPage.save();
        expect(carDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CarComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-car div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CarDialogPage {
    modalTitle = element(by.css('h4#myCarLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    ownerSelect = element(by.css('select#field_owner'));
    driverSelect = element(by.css('select#field_driver'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    };

    ownerSelectLastOption = function() {
        this.ownerSelect.all(by.tagName('option')).last().click();
    };

    ownerSelectOption = function(option) {
        this.ownerSelect.sendKeys(option);
    };

    getOwnerSelect = function() {
        return this.ownerSelect;
    };

    getOwnerSelectedOption = function() {
        return this.ownerSelect.element(by.css('option:checked')).getText();
    };

    driverSelectLastOption = function() {
        this.driverSelect.all(by.tagName('option')).last().click();
    };

    driverSelectOption = function(option) {
        this.driverSelect.sendKeys(option);
    };

    getDriverSelect = function() {
        return this.driverSelect;
    };

    getDriverSelectedOption = function() {
        return this.driverSelect.element(by.css('option:checked')).getText();
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
