import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('DesignerShow e2e test', () => {

    let navBarPage: NavBarPage;
    let designerShowDialogPage: DesignerShowDialogPage;
    let designerShowComponentsPage: DesignerShowComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load DesignerShows', () => {
        navBarPage.goToEntity('designer-show');
        designerShowComponentsPage = new DesignerShowComponentsPage();
        expect(designerShowComponentsPage.getTitle())
            .toMatch(/senseBrandApp.designerShow.home.title/);

    });

    it('should load create DesignerShow dialog', () => {
        designerShowComponentsPage.clickOnCreateButton();
        designerShowDialogPage = new DesignerShowDialogPage();
        expect(designerShowDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.designerShow.home.createOrEditLabel/);
        designerShowDialogPage.close();
    });

   /* it('should create and save DesignerShows', () => {
        designerShowComponentsPage.clickOnCreateButton();
        designerShowDialogPage.setFirstLevelTitleInput('firstLevelTitle');
        expect(designerShowDialogPage.getFirstLevelTitleInput()).toMatch('firstLevelTitle');
        designerShowDialogPage.setSecondLevelTitleInput('secondLevelTitle');
        expect(designerShowDialogPage.getSecondLevelTitleInput()).toMatch('secondLevelTitle');
        designerShowDialogPage.setIntroductionInput('introduction');
        expect(designerShowDialogPage.getIntroductionInput()).toMatch('introduction');
        designerShowDialogPage.designerSelectLastOption();
        designerShowDialogPage.save();
        expect(designerShowDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class DesignerShowComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-designer-show div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class DesignerShowDialogPage {
    modalTitle = element(by.css('h4#myDesignerShowLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    firstLevelTitleInput = element(by.css('input#field_firstLevelTitle'));
    secondLevelTitleInput = element(by.css('input#field_secondLevelTitle'));
    introductionInput = element(by.css('textarea#field_introduction'));
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
