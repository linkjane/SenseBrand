import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Designer e2e test', () => {

    let navBarPage: NavBarPage;
    let designerDialogPage: DesignerDialogPage;
    let designerComponentsPage: DesignerComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Designers', () => {
        navBarPage.goToEntity('designer');
        designerComponentsPage = new DesignerComponentsPage();
        expect(designerComponentsPage.getTitle())
            .toMatch(/senseBrandApp.designer.home.title/);

    });

    it('should load create Designer dialog', () => {
        designerComponentsPage.clickOnCreateButton();
        designerDialogPage = new DesignerDialogPage();
        expect(designerDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.designer.home.createOrEditLabel/);
        designerDialogPage.close();
    });

    it('should create and save Designers', () => {
        designerComponentsPage.clickOnCreateButton();
        designerDialogPage.setZnNameInput('znName');
        expect(designerDialogPage.getZnNameInput()).toMatch('znName');
        designerDialogPage.setEnNameInput('enName');
        expect(designerDialogPage.getEnNameInput()).toMatch('enName');
        designerDialogPage.setProfileBackFileInput('profileBackFile');
        expect(designerDialogPage.getProfileBackFileInput()).toMatch('profileBackFile');
        designerDialogPage.setProfileThumbnailFileInput('profileThumbnailFile');
        expect(designerDialogPage.getProfileThumbnailFileInput()).toMatch('profileThumbnailFile');
        designerDialogPage.setPositionInput('position');
        expect(designerDialogPage.getPositionInput()).toMatch('position');
        designerDialogPage.setIntroductionInput('introduction');
        expect(designerDialogPage.getIntroductionInput()).toMatch('introduction');
        designerDialogPage.getIsShowInput().isSelected().then((selected) => {
            if (selected) {
                designerDialogPage.getIsShowInput().click();
                expect(designerDialogPage.getIsShowInput().isSelected()).toBeFalsy();
            } else {
                designerDialogPage.getIsShowInput().click();
                expect(designerDialogPage.getIsShowInput().isSelected()).toBeTruthy();
            }
        });
        designerDialogPage.save();
        expect(designerDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class DesignerComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-designer div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class DesignerDialogPage {
    modalTitle = element(by.css('h4#myDesignerLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    znNameInput = element(by.css('input#field_znName'));
    enNameInput = element(by.css('input#field_enName'));
    profileBackFileInput = element(by.css('input#field_profileBackFile'));
    profileThumbnailFileInput = element(by.css('input#field_profileThumbnailFile'));
    positionInput = element(by.css('input#field_position'));
    introductionInput = element(by.css('textarea#field_introduction'));
    isShowInput = element(by.css('input#field_isShow'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setZnNameInput = function(znName) {
        this.znNameInput.sendKeys(znName);
    };

    getZnNameInput = function() {
        return this.znNameInput.getAttribute('value');
    };

    setEnNameInput = function(enName) {
        this.enNameInput.sendKeys(enName);
    };

    getEnNameInput = function() {
        return this.enNameInput.getAttribute('value');
    };

    setProfileBackFileInput = function(profileBackFile) {
        this.profileBackFileInput.sendKeys(profileBackFile);
    };

    getProfileBackFileInput = function() {
        return this.profileBackFileInput.getAttribute('value');
    };

    setProfileThumbnailFileInput = function(profileThumbnailFile) {
        this.profileThumbnailFileInput.sendKeys(profileThumbnailFile);
    };

    getProfileThumbnailFileInput = function() {
        return this.profileThumbnailFileInput.getAttribute('value');
    };

    setPositionInput = function(position) {
        this.positionInput.sendKeys(position);
    };

    getPositionInput = function() {
        return this.positionInput.getAttribute('value');
    };

    setIntroductionInput = function(introduction) {
        this.introductionInput.sendKeys(introduction);
    };

    getIntroductionInput = function() {
        return this.introductionInput.getAttribute('value');
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
