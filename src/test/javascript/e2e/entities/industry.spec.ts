import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Industry e2e test', () => {

    let navBarPage: NavBarPage;
    let industryDialogPage: IndustryDialogPage;
    let industryComponentsPage: IndustryComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Industries', () => {
        navBarPage.goToEntity('industry');
        industryComponentsPage = new IndustryComponentsPage();
        expect(industryComponentsPage.getTitle())
            .toMatch(/senseBrandApp.industry.home.title/);

    });

    it('should load create Industry dialog', () => {
        industryComponentsPage.clickOnCreateButton();
        industryDialogPage = new IndustryDialogPage();
        expect(industryDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.industry.home.createOrEditLabel/);
        industryDialogPage.close();
    });

    it('should create and save Industries', () => {
        industryComponentsPage.clickOnCreateButton();
        industryDialogPage.setNameInput('name');
        expect(industryDialogPage.getNameInput()).toMatch('name');
        industryDialogPage.setSolutionLinkInput('solutionLink');
        expect(industryDialogPage.getSolutionLinkInput()).toMatch('solutionLink');
        industryDialogPage.setAnalysisLinkInput('analysisLink');
        expect(industryDialogPage.getAnalysisLinkInput()).toMatch('analysisLink');
        industryDialogPage.setInventoryFileInput('inventoryFile');
        expect(industryDialogPage.getInventoryFileInput()).toMatch('inventoryFile');
        industryDialogPage.getIsShowInput().isSelected().then((selected) => {
            if (selected) {
                industryDialogPage.getIsShowInput().click();
                expect(industryDialogPage.getIsShowInput().isSelected()).toBeFalsy();
            } else {
                industryDialogPage.getIsShowInput().click();
                expect(industryDialogPage.getIsShowInput().isSelected()).toBeTruthy();
            }
        });
        industryDialogPage.save();
        expect(industryDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class IndustryComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-industry div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class IndustryDialogPage {
    modalTitle = element(by.css('h4#myIndustryLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    solutionLinkInput = element(by.css('input#field_solutionLink'));
    analysisLinkInput = element(by.css('input#field_analysisLink'));
    inventoryFileInput = element(by.css('input#field_inventoryFile'));
    isShowInput = element(by.css('input#field_isShow'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    };

    setSolutionLinkInput = function(solutionLink) {
        this.solutionLinkInput.sendKeys(solutionLink);
    };

    getSolutionLinkInput = function() {
        return this.solutionLinkInput.getAttribute('value');
    };

    setAnalysisLinkInput = function(analysisLink) {
        this.analysisLinkInput.sendKeys(analysisLink);
    };

    getAnalysisLinkInput = function() {
        return this.analysisLinkInput.getAttribute('value');
    };

    setInventoryFileInput = function(inventoryFile) {
        this.inventoryFileInput.sendKeys(inventoryFile);
    };

    getInventoryFileInput = function() {
        return this.inventoryFileInput.getAttribute('value');
    };

    getIsShowInput = function() {
        return this.isShowInput;
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
