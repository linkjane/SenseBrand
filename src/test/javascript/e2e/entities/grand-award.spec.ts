import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('GrandAward e2e test', () => {

    let navBarPage: NavBarPage;
    let grandAwardDialogPage: GrandAwardDialogPage;
    let grandAwardComponentsPage: GrandAwardComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load GrandAwards', () => {
        navBarPage.goToEntity('grand-award');
        grandAwardComponentsPage = new GrandAwardComponentsPage();
        expect(grandAwardComponentsPage.getTitle())
            .toMatch(/senseBrandApp.grandAward.home.title/);

    });

    it('should load create GrandAward dialog', () => {
        grandAwardComponentsPage.clickOnCreateButton();
        grandAwardDialogPage = new GrandAwardDialogPage();
        expect(grandAwardDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.grandAward.home.createOrEditLabel/);
        grandAwardDialogPage.close();
    });

    it('should create and save GrandAwards', () => {
        grandAwardComponentsPage.clickOnCreateButton();
        grandAwardDialogPage.setTitleInput('title');
        expect(grandAwardDialogPage.getTitleInput()).toMatch('title');
        grandAwardDialogPage.setImgFileInput('imgFile');
        expect(grandAwardDialogPage.getImgFileInput()).toMatch('imgFile');
        grandAwardDialogPage.setIntroductionInput('introduction');
        expect(grandAwardDialogPage.getIntroductionInput()).toMatch('introduction');
        grandAwardDialogPage.setDetailLinkInput('detailLink');
        expect(grandAwardDialogPage.getDetailLinkInput()).toMatch('detailLink');
        grandAwardDialogPage.save();
        expect(grandAwardDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class GrandAwardComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-grand-award div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class GrandAwardDialogPage {
    modalTitle = element(by.css('h4#myGrandAwardLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));
    imgFileInput = element(by.css('input#field_imgFile'));
    introductionInput = element(by.css('textarea#field_introduction'));
    detailLinkInput = element(by.css('input#field_detailLink'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTitleInput = function(title) {
        this.titleInput.sendKeys(title);
    };

    getTitleInput = function() {
        return this.titleInput.getAttribute('value');
    };

    setImgFileInput = function(imgFile) {
        this.imgFileInput.sendKeys(imgFile);
    };

    getImgFileInput = function() {
        return this.imgFileInput.getAttribute('value');
    };

    setIntroductionInput = function(introduction) {
        this.introductionInput.sendKeys(introduction);
    };

    getIntroductionInput = function() {
        return this.introductionInput.getAttribute('value');
    };

    setDetailLinkInput = function(detailLink) {
        this.detailLinkInput.sendKeys(detailLink);
    };

    getDetailLinkInput = function() {
        return this.detailLinkInput.getAttribute('value');
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
