import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('DesignerSentiment e2e test', () => {

    let navBarPage: NavBarPage;
    let designerSentimentDialogPage: DesignerSentimentDialogPage;
    let designerSentimentComponentsPage: DesignerSentimentComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load DesignerSentiments', () => {
        navBarPage.goToEntity('designer-sentiment');
        designerSentimentComponentsPage = new DesignerSentimentComponentsPage();
        expect(designerSentimentComponentsPage.getTitle())
            .toMatch(/senseBrandApp.designerSentiment.home.title/);

    });

    it('should load create DesignerSentiment dialog', () => {
        designerSentimentComponentsPage.clickOnCreateButton();
        designerSentimentDialogPage = new DesignerSentimentDialogPage();
        expect(designerSentimentDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.designerSentiment.home.createOrEditLabel/);
        designerSentimentDialogPage.close();
    });

   /* it('should create and save DesignerSentiments', () => {
        designerSentimentComponentsPage.clickOnCreateButton();
        designerSentimentDialogPage.setFirstLevelTitleInput('firstLevelTitle');
        expect(designerSentimentDialogPage.getFirstLevelTitleInput()).toMatch('firstLevelTitle');
        designerSentimentDialogPage.setSecondLevelTitleInput('secondLevelTitle');
        expect(designerSentimentDialogPage.getSecondLevelTitleInput()).toMatch('secondLevelTitle');
        designerSentimentDialogPage.setImgFileInput('imgFile');
        expect(designerSentimentDialogPage.getImgFileInput()).toMatch('imgFile');
        designerSentimentDialogPage.designerSelectLastOption();
        designerSentimentDialogPage.save();
        expect(designerSentimentDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class DesignerSentimentComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-designer-sentiment div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class DesignerSentimentDialogPage {
    modalTitle = element(by.css('h4#myDesignerSentimentLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    firstLevelTitleInput = element(by.css('input#field_firstLevelTitle'));
    secondLevelTitleInput = element(by.css('input#field_secondLevelTitle'));
    imgFileInput = element(by.css('input#field_imgFile'));
    designerSelect = element(by.css('select#field_designer'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setFirstLevelTitleInput = function(firstLevelTitle) {
        this.firstLevelTitleInput.sendKeys(firstLevelTitle);
    };

    getFirstLevelTitleInput = function() {
        return this.firstLevelTitleInput.getAttribute('value');
    };

    setSecondLevelTitleInput = function(secondLevelTitle) {
        this.secondLevelTitleInput.sendKeys(secondLevelTitle);
    };

    getSecondLevelTitleInput = function() {
        return this.secondLevelTitleInput.getAttribute('value');
    };

    setImgFileInput = function(imgFile) {
        this.imgFileInput.sendKeys(imgFile);
    };

    getImgFileInput = function() {
        return this.imgFileInput.getAttribute('value');
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
