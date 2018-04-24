import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('IndustryAll e2e test', () => {

    let navBarPage: NavBarPage;
    let industryAllDialogPage: IndustryAllDialogPage;
    let industryAllComponentsPage: IndustryAllComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load IndustryAlls', () => {
        navBarPage.goToEntity('industry-all');
        industryAllComponentsPage = new IndustryAllComponentsPage();
        expect(industryAllComponentsPage.getTitle())
            .toMatch(/senseBrandApp.industryAll.home.title/);

    });

    it('should load create IndustryAll dialog', () => {
        industryAllComponentsPage.clickOnCreateButton();
        industryAllDialogPage = new IndustryAllDialogPage();
        expect(industryAllDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.industryAll.home.createOrEditLabel/);
        industryAllDialogPage.close();
    });

    it('should create and save IndustryAlls', () => {
        industryAllComponentsPage.clickOnCreateButton();
        industryAllDialogPage.setNameInput('name');
        expect(industryAllDialogPage.getNameInput()).toMatch('name');
        industryAllDialogPage.save();
        expect(industryAllDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class IndustryAllComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-industry-all div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class IndustryAllDialogPage {
    modalTitle = element(by.css('h4#myIndustryAllLabel'));
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
