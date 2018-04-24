import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('IndustryTypeName e2e test', () => {

    let navBarPage: NavBarPage;
    let industryTypeNameDialogPage: IndustryTypeNameDialogPage;
    let industryTypeNameComponentsPage: IndustryTypeNameComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load IndustryTypeNames', () => {
        navBarPage.goToEntity('industry-type-name');
        industryTypeNameComponentsPage = new IndustryTypeNameComponentsPage();
        expect(industryTypeNameComponentsPage.getTitle())
            .toMatch(/senseBrandApp.industryTypeName.home.title/);

    });

    it('should load create IndustryTypeName dialog', () => {
        industryTypeNameComponentsPage.clickOnCreateButton();
        industryTypeNameDialogPage = new IndustryTypeNameDialogPage();
        expect(industryTypeNameDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.industryTypeName.home.createOrEditLabel/);
        industryTypeNameDialogPage.close();
    });

    it('should create and save IndustryTypeNames', () => {
        industryTypeNameComponentsPage.clickOnCreateButton();
        industryTypeNameDialogPage.setNameInput('name');
        expect(industryTypeNameDialogPage.getNameInput()).toMatch('name');
        industryTypeNameDialogPage.setIntroductionInput('introduction');
        expect(industryTypeNameDialogPage.getIntroductionInput()).toMatch('introduction');
        industryTypeNameDialogPage.setIconFileInput('iconFile');
        expect(industryTypeNameDialogPage.getIconFileInput()).toMatch('iconFile');
        industryTypeNameDialogPage.industryTypeSelectLastOption();
        industryTypeNameDialogPage.save();
        expect(industryTypeNameDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class IndustryTypeNameComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-industry-type-name div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class IndustryTypeNameDialogPage {
    modalTitle = element(by.css('h4#myIndustryTypeNameLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    introductionInput = element(by.css('textarea#field_introduction'));
    iconFileInput = element(by.css('input#field_iconFile'));
    industryTypeSelect = element(by.css('select#field_industryType'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    };

    setIntroductionInput = function(introduction) {
        this.introductionInput.sendKeys(introduction);
    };

    getIntroductionInput = function() {
        return this.introductionInput.getAttribute('value');
    };

    setIconFileInput = function(iconFile) {
        this.iconFileInput.sendKeys(iconFile);
    };

    getIconFileInput = function() {
        return this.iconFileInput.getAttribute('value');
    };

    industryTypeSelectLastOption = function() {
        this.industryTypeSelect.all(by.tagName('option')).last().click();
    };

    industryTypeSelectOption = function(option) {
        this.industryTypeSelect.sendKeys(option);
    };

    getIndustryTypeSelect = function() {
        return this.industryTypeSelect;
    };

    getIndustryTypeSelectedOption = function() {
        return this.industryTypeSelect.element(by.css('option:checked')).getText();
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
