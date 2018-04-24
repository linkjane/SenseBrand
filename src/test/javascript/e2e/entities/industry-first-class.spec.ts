import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('IndustryFirstClass e2e test', () => {

    let navBarPage: NavBarPage;
    let industryFirstClassDialogPage: IndustryFirstClassDialogPage;
    let industryFirstClassComponentsPage: IndustryFirstClassComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load IndustryFirstClasses', () => {
        navBarPage.goToEntity('industry-first-class');
        industryFirstClassComponentsPage = new IndustryFirstClassComponentsPage();
        expect(industryFirstClassComponentsPage.getTitle())
            .toMatch(/senseBrandApp.industryFirstClass.home.title/);

    });

    it('should load create IndustryFirstClass dialog', () => {
        industryFirstClassComponentsPage.clickOnCreateButton();
        industryFirstClassDialogPage = new IndustryFirstClassDialogPage();
        expect(industryFirstClassDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.industryFirstClass.home.createOrEditLabel/);
        industryFirstClassDialogPage.close();
    });

   /* it('should create and save IndustryFirstClasses', () => {
        industryFirstClassComponentsPage.clickOnCreateButton();
        industryFirstClassDialogPage.setNameInput('name');
        expect(industryFirstClassDialogPage.getNameInput()).toMatch('name');
        industryFirstClassDialogPage.industryAllSelectLastOption();
        industryFirstClassDialogPage.save();
        expect(industryFirstClassDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class IndustryFirstClassComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-industry-first-class div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class IndustryFirstClassDialogPage {
    modalTitle = element(by.css('h4#myIndustryFirstClassLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    industryAllSelect = element(by.css('select#field_industryAll'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    };

    industryAllSelectLastOption = function() {
        this.industryAllSelect.all(by.tagName('option')).last().click();
    };

    industryAllSelectOption = function(option) {
        this.industryAllSelect.sendKeys(option);
    };

    getIndustryAllSelect = function() {
        return this.industryAllSelect;
    };

    getIndustryAllSelectedOption = function() {
        return this.industryAllSelect.element(by.css('option:checked')).getText();
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
