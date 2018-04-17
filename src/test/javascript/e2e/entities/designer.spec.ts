import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Designer e2e test', () => {

    let navBarPage: NavBarPage;
    let designerDialogPage: DesignerDialogPage;
    let designerComponentsPage: DesignerComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Designers', () => {
        navBarPage.goToEntity('designer');
        designerComponentsPage = new DesignerComponentsPage();
        expect(designerComponentsPage.getTitle())
            .toMatch(/senseBrandApp.designer.home.title/);

    });

    it('should load create Designer dialog', () => {
        designerComponentsPage.clickOnCreateButton();
        designerDialogPage = new DesignerDialogPage();
        expect(designerDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.designer.home.createOrEditLabel/);
        designerDialogPage.close();
    });

    it('should create and save Designers', () => {
        designerComponentsPage.clickOnCreateButton();
        designerDialogPage.setNameInput('name');
        expect(designerDialogPage.getNameInput()).toMatch('name');
        designerDialogPage.setEnglishNameInput('englishName');
        expect(designerDialogPage.getEnglishNameInput()).toMatch('englishName');
        designerDialogPage.setPositionInput('position');
        expect(designerDialogPage.getPositionInput()).toMatch('position');
        designerDialogPage.save();
        expect(designerDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class DesignerComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-designer div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class DesignerDialogPage {
    modalTitle = element(by.css('h4#myDesignerLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    englishNameInput = element(by.css('input#field_englishName'));
    positionInput = element(by.css('input#field_position'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    };

    setEnglishNameInput = function(englishName) {
        this.englishNameInput.sendKeys(englishName);
    };

    getEnglishNameInput = function() {
        return this.englishNameInput.getAttribute('value');
    };

    setPositionInput = function(position) {
        this.positionInput.sendKeys(position);
    };

    getPositionInput = function() {
        return this.positionInput.getAttribute('value');
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
