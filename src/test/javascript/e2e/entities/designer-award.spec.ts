import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('DesignerAward e2e test', () => {

    let navBarPage: NavBarPage;
    let designerAwardDialogPage: DesignerAwardDialogPage;
    let designerAwardComponentsPage: DesignerAwardComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load DesignerAwards', () => {
        navBarPage.goToEntity('designer-award');
        designerAwardComponentsPage = new DesignerAwardComponentsPage();
        expect(designerAwardComponentsPage.getTitle())
            .toMatch(/senseBrandApp.designerAward.home.title/);

    });

    it('should load create DesignerAward dialog', () => {
        designerAwardComponentsPage.clickOnCreateButton();
        designerAwardDialogPage = new DesignerAwardDialogPage();
        expect(designerAwardDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.designerAward.home.createOrEditLabel/);
        designerAwardDialogPage.close();
    });

   /* it('should create and save DesignerAwards', () => {
        designerAwardComponentsPage.clickOnCreateButton();
        designerAwardDialogPage.setTitleInput('title');
        expect(designerAwardDialogPage.getTitleInput()).toMatch('title');
        designerAwardDialogPage.setIntroductionInput('introduction');
        expect(designerAwardDialogPage.getIntroductionInput()).toMatch('introduction');
        designerAwardDialogPage.setImgFileInput('imgFile');
        expect(designerAwardDialogPage.getImgFileInput()).toMatch('imgFile');
        designerAwardDialogPage.setDetailLinkInput('detailLink');
        expect(designerAwardDialogPage.getDetailLinkInput()).toMatch('detailLink');
        designerAwardDialogPage.designerSelectLastOption();
        designerAwardDialogPage.save();
        expect(designerAwardDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class DesignerAwardComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-designer-award div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class DesignerAwardDialogPage {
    modalTitle = element(by.css('h4#myDesignerAwardLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));
    introductionInput = element(by.css('textarea#field_introduction'));
    imgFileInput = element(by.css('input#field_imgFile'));
    detailLinkInput = element(by.css('input#field_detailLink'));
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

    setIntroductionInput = function(introduction) {
        this.introductionInput.sendKeys(introduction);
    };

    getIntroductionInput = function() {
        return this.introductionInput.getAttribute('value');
    };

    setImgFileInput = function(imgFile) {
        this.imgFileInput.sendKeys(imgFile);
    };

    getImgFileInput = function() {
        return this.imgFileInput.getAttribute('value');
    };

    setDetailLinkInput = function(detailLink) {
        this.detailLinkInput.sendKeys(detailLink);
    };

    getDetailLinkInput = function() {
        return this.detailLinkInput.getAttribute('value');
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
