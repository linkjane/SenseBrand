import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('DesignerShowImg e2e test', () => {

    let navBarPage: NavBarPage;
    let designerShowImgDialogPage: DesignerShowImgDialogPage;
    let designerShowImgComponentsPage: DesignerShowImgComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load DesignerShowImgs', () => {
        navBarPage.goToEntity('designer-show-img');
        designerShowImgComponentsPage = new DesignerShowImgComponentsPage();
        expect(designerShowImgComponentsPage.getTitle())
            .toMatch(/senseBrandApp.designerShowImg.home.title/);

    });

    it('should load create DesignerShowImg dialog', () => {
        designerShowImgComponentsPage.clickOnCreateButton();
        designerShowImgDialogPage = new DesignerShowImgDialogPage();
        expect(designerShowImgDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.designerShowImg.home.createOrEditLabel/);
        designerShowImgDialogPage.close();
    });

   /* it('should create and save DesignerShowImgs', () => {
        designerShowImgComponentsPage.clickOnCreateButton();
        designerShowImgDialogPage.setImgTileInput('imgTile');
        expect(designerShowImgDialogPage.getImgTileInput()).toMatch('imgTile');
        designerShowImgDialogPage.setImgFileInput('imgFile');
        expect(designerShowImgDialogPage.getImgFileInput()).toMatch('imgFile');
        designerShowImgDialogPage.designerSelectLastOption();
        designerShowImgDialogPage.save();
        expect(designerShowImgDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });*/

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class DesignerShowImgComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-designer-show-img div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class DesignerShowImgDialogPage {
    modalTitle = element(by.css('h4#myDesignerShowImgLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    imgTileInput = element(by.css('input#field_imgTile'));
    imgFileInput = element(by.css('input#field_imgFile'));
    designerSelect = element(by.css('select#field_designer'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setImgTileInput = function(imgTile) {
        this.imgTileInput.sendKeys(imgTile);
    };

    getImgTileInput = function() {
        return this.imgTileInput.getAttribute('value');
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
