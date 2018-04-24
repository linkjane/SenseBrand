import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('OurSight e2e test', () => {

    let navBarPage: NavBarPage;
    let ourSightDialogPage: OurSightDialogPage;
    let ourSightComponentsPage: OurSightComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load OurSights', () => {
        navBarPage.goToEntity('our-sight');
        ourSightComponentsPage = new OurSightComponentsPage();
        expect(ourSightComponentsPage.getTitle())
            .toMatch(/senseBrandApp.ourSight.home.title/);

    });

    it('should load create OurSight dialog', () => {
        ourSightComponentsPage.clickOnCreateButton();
        ourSightDialogPage = new OurSightDialogPage();
        expect(ourSightDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.ourSight.home.createOrEditLabel/);
        ourSightDialogPage.close();
    });

    it('should create and save OurSights', () => {
        ourSightComponentsPage.clickOnCreateButton();
        ourSightDialogPage.setTitleInput('title');
        expect(ourSightDialogPage.getTitleInput()).toMatch('title');
        ourSightDialogPage.setKeyWordInput('keyWord');
        expect(ourSightDialogPage.getKeyWordInput()).toMatch('keyWord');
        ourSightDialogPage.setImgFileInput('imgFile');
        expect(ourSightDialogPage.getImgFileInput()).toMatch('imgFile');
        ourSightDialogPage.save();
        expect(ourSightDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class OurSightComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-our-sight div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class OurSightDialogPage {
    modalTitle = element(by.css('h4#myOurSightLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));
    keyWordInput = element(by.css('input#field_keyWord'));
    imgFileInput = element(by.css('input#field_imgFile'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTitleInput = function(title) {
        this.titleInput.sendKeys(title);
    };

    getTitleInput = function() {
        return this.titleInput.getAttribute('value');
    };

    setKeyWordInput = function(keyWord) {
        this.keyWordInput.sendKeys(keyWord);
    };

    getKeyWordInput = function() {
        return this.keyWordInput.getAttribute('value');
    };

    setImgFileInput = function(imgFile) {
        this.imgFileInput.sendKeys(imgFile);
    };

    getImgFileInput = function() {
        return this.imgFileInput.getAttribute('value');
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
