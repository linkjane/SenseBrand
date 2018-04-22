import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('DesignerIdeaDetails e2e test', () => {

    let navBarPage: NavBarPage;
    let designerIdeaDetailsDialogPage: DesignerIdeaDetailsDialogPage;
    let designerIdeaDetailsComponentsPage: DesignerIdeaDetailsComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load DesignerIdeaDetails', () => {
        navBarPage.goToEntity('designer-idea-details');
        designerIdeaDetailsComponentsPage = new DesignerIdeaDetailsComponentsPage();
        expect(designerIdeaDetailsComponentsPage.getTitle())
            .toMatch(/senseBrandApp.designerIdeaDetails.home.title/);

    });

    it('should load create DesignerIdeaDetails dialog', () => {
        designerIdeaDetailsComponentsPage.clickOnCreateButton();
        designerIdeaDetailsDialogPage = new DesignerIdeaDetailsDialogPage();
        expect(designerIdeaDetailsDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.designerIdeaDetails.home.createOrEditLabel/);
        designerIdeaDetailsDialogPage.close();
    });

   /* it('should create and save DesignerIdeaDetails', () => {
        designerIdeaDetailsComponentsPage.clickOnCreateButton();
        designerIdeaDetailsDialogPage.setTitleInput('title');
        expect(designerIdeaDetailsDialogPage.getTitleInput()).toMatch('title');
        designerIdeaDetailsDialogPage.setShareTimeInput('2000-12-31');
        expect(designerIdeaDetailsDialogPage.getShareTimeInput()).toMatch('2000-12-31');
        designerIdeaDetailsDialogPage.setSecondLevelTitleInput('secondLevelTitle');
        expect(designerIdeaDetailsDialogPage.getSecondLevelTitleInput()).toMatch('secondLevelTitle');
        designerIdeaDetailsDialogPage.setIntroductionInput('introduction');
        expect(designerIdeaDetailsDialogPage.getIntroductionInput()).toMatch('introduction');
        designerIdeaDetailsDialogPage.designerSelectLastOption();
        designerIdeaDetailsDialogPage.save();
        expect(designerIdeaDetailsDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class DesignerIdeaDetailsComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-designer-idea-details div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class DesignerIdeaDetailsDialogPage {
    modalTitle = element(by.css('h4#myDesignerIdeaDetailsLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));
    shareTimeInput = element(by.css('input#field_shareTime'));
    secondLevelTitleInput = element(by.css('input#field_secondLevelTitle'));
    introductionInput = element(by.css('textarea#field_introduction'));
    designerSelect = element(by.css('select#field_designer'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTitleInput = function(title) {
        this.titleInput.sendKeys(title);
    };

    getTitleInput = function() {
        return this.titleInput.getAttribute('value');
    };

    setShareTimeInput = function(shareTime) {
        this.shareTimeInput.sendKeys(shareTime);
    };

    getShareTimeInput = function() {
        return this.shareTimeInput.getAttribute('value');
    };

    setSecondLevelTitleInput = function(secondLevelTitle) {
        this.secondLevelTitleInput.sendKeys(secondLevelTitle);
    };

    getSecondLevelTitleInput = function() {
        return this.secondLevelTitleInput.getAttribute('value');
    };

    setIntroductionInput = function(introduction) {
        this.introductionInput.sendKeys(introduction);
    };

    getIntroductionInput = function() {
        return this.introductionInput.getAttribute('value');
    };

    designerSelectLastOption = function() {
        this.designerSelect.all(by.tagName('option')).last().click();
    };

    designerSelectOption = function(option) {
        this.designerSelect.sendKeys(option);
    };

    getDesignerSelect = function() {
        return this.designerSelect;
    };

    getDesignerSelectedOption = function() {
        return this.designerSelect.element(by.css('option:checked')).getText();
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
