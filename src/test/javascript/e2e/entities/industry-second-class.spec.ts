import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('IndustrySecondClass e2e test', () => {

    let navBarPage: NavBarPage;
    let industrySecondClassDialogPage: IndustrySecondClassDialogPage;
    let industrySecondClassComponentsPage: IndustrySecondClassComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load IndustrySecondClasses', () => {
        navBarPage.goToEntity('industry-second-class');
        industrySecondClassComponentsPage = new IndustrySecondClassComponentsPage();
        expect(industrySecondClassComponentsPage.getTitle())
            .toMatch(/senseBrandApp.industrySecondClass.home.title/);

    });

    it('should load create IndustrySecondClass dialog', () => {
        industrySecondClassComponentsPage.clickOnCreateButton();
        industrySecondClassDialogPage = new IndustrySecondClassDialogPage();
        expect(industrySecondClassDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.industrySecondClass.home.createOrEditLabel/);
        industrySecondClassDialogPage.close();
    });

    it('should create and save IndustrySecondClasses', () => {
        industrySecondClassComponentsPage.clickOnCreateButton();
        industrySecondClassDialogPage.setNameInput('name');
        expect(industrySecondClassDialogPage.getNameInput()).toMatch('name');
        industrySecondClassDialogPage.industryFirstClassSelectLastOption();
        industrySecondClassDialogPage.save();
        expect(industrySecondClassDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class IndustrySecondClassComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-industry-second-class div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class IndustrySecondClassDialogPage {
    modalTitle = element(by.css('h4#myIndustrySecondClassLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    industryFirstClassSelect = element(by.css('select#field_industryFirstClass'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    };

    industryFirstClassSelectLastOption = function() {
        this.industryFirstClassSelect.all(by.tagName('option')).last().click();
    };

    industryFirstClassSelectOption = function(option) {
        this.industryFirstClassSelect.sendKeys(option);
    };

    getIndustryFirstClassSelect = function() {
        return this.industryFirstClassSelect;
    };

    getIndustryFirstClassSelectedOption = function() {
        return this.industryFirstClassSelect.element(by.css('option:checked')).getText();
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
