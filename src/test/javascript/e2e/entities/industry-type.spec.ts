import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('IndustryType e2e test', () => {

    let navBarPage: NavBarPage;
    let industryTypeDialogPage: IndustryTypeDialogPage;
    let industryTypeComponentsPage: IndustryTypeComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load IndustryTypes', () => {
        navBarPage.goToEntity('industry-type');
        industryTypeComponentsPage = new IndustryTypeComponentsPage();
        expect(industryTypeComponentsPage.getTitle())
            .toMatch(/senseBrandApp.industryType.home.title/);

    });

    it('should load create IndustryType dialog', () => {
        industryTypeComponentsPage.clickOnCreateButton();
        industryTypeDialogPage = new IndustryTypeDialogPage();
        expect(industryTypeDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.industryType.home.createOrEditLabel/);
        industryTypeDialogPage.close();
    });

    it('should create and save IndustryTypes', () => {
        industryTypeComponentsPage.clickOnCreateButton();
        industryTypeDialogPage.setNameInput('name');
        expect(industryTypeDialogPage.getNameInput()).toMatch('name');
        industryTypeDialogPage.setSmallTitleInput('smallTitle');
        expect(industryTypeDialogPage.getSmallTitleInput()).toMatch('smallTitle');
        industryTypeDialogPage.save();
        expect(industryTypeDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class IndustryTypeComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-industry-type div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class IndustryTypeDialogPage {
    modalTitle = element(by.css('h4#myIndustryTypeLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    smallTitleInput = element(by.css('input#field_smallTitle'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    };

    setSmallTitleInput = function(smallTitle) {
        this.smallTitleInput.sendKeys(smallTitle);
    };

    getSmallTitleInput = function() {
        return this.smallTitleInput.getAttribute('value');
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
