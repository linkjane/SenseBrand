import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('IndustryTop e2e test', () => {

    let navBarPage: NavBarPage;
    let industryTopDialogPage: IndustryTopDialogPage;
    let industryTopComponentsPage: IndustryTopComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load IndustryTops', () => {
        navBarPage.goToEntity('industry-top');
        industryTopComponentsPage = new IndustryTopComponentsPage();
        expect(industryTopComponentsPage.getTitle())
            .toMatch(/senseBrandApp.industryTop.home.title/);

    });

    it('should load create IndustryTop dialog', () => {
        industryTopComponentsPage.clickOnCreateButton();
        industryTopDialogPage = new IndustryTopDialogPage();
        expect(industryTopDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.industryTop.home.createOrEditLabel/);
        industryTopDialogPage.close();
    });

    it('should create and save IndustryTops', () => {
        industryTopComponentsPage.clickOnCreateButton();
        industryTopDialogPage.setNameInput('name');
        expect(industryTopDialogPage.getNameInput()).toMatch('name');
        industryTopDialogPage.setGoLinkInput('goLink');
        expect(industryTopDialogPage.getGoLinkInput()).toMatch('goLink');
        industryTopDialogPage.industrySelectLastOption();
        industryTopDialogPage.save();
        expect(industryTopDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class IndustryTopComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-industry-top div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class IndustryTopDialogPage {
    modalTitle = element(by.css('h4#myIndustryTopLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    goLinkInput = element(by.css('input#field_goLink'));
    industrySelect = element(by.css('select#field_industry'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    };

    setGoLinkInput = function(goLink) {
        this.goLinkInput.sendKeys(goLink);
    };

    getGoLinkInput = function() {
        return this.goLinkInput.getAttribute('value');
    };

    industrySelectLastOption = function() {
        this.industrySelect.all(by.tagName('option')).last().click();
    };

    industrySelectOption = function(option) {
        this.industrySelect.sendKeys(option);
    };

    getIndustrySelect = function() {
        return this.industrySelect;
    };

    getIndustrySelectedOption = function() {
        return this.industrySelect.element(by.css('option:checked')).getText();
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
