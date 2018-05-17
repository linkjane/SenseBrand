import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('DesignerIdeaMedia e2e test', () => {

    let navBarPage: NavBarPage;
    let designerIdeaMediaDialogPage: DesignerIdeaMediaDialogPage;
    let designerIdeaMediaComponentsPage: DesignerIdeaMediaComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load DesignerIdeaMedias', () => {
        navBarPage.goToEntity('designer-idea-media');
        designerIdeaMediaComponentsPage = new DesignerIdeaMediaComponentsPage();
        expect(designerIdeaMediaComponentsPage.getTitle())
            .toMatch(/senseBrandApp.designerIdeaMedia.home.title/);

    });

    it('should load create DesignerIdeaMedia dialog', () => {
        designerIdeaMediaComponentsPage.clickOnCreateButton();
        designerIdeaMediaDialogPage = new DesignerIdeaMediaDialogPage();
        expect(designerIdeaMediaDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.designerIdeaMedia.home.createOrEditLabel/);
        designerIdeaMediaDialogPage.close();
    });

    it('should create and save DesignerIdeaMedias', () => {
        designerIdeaMediaComponentsPage.clickOnCreateButton();
        designerIdeaMediaDialogPage.setTitleInput('title');
        expect(designerIdeaMediaDialogPage.getTitleInput()).toMatch('title');
        designerIdeaMediaDialogPage.setShareTimeInput('2000-12-31');
        expect(designerIdeaMediaDialogPage.getShareTimeInput()).toMatch('2000-12-31');
        designerIdeaMediaDialogPage.setIntroductionInput('introduction');
        expect(designerIdeaMediaDialogPage.getIntroductionInput()).toMatch('introduction');
        designerIdeaMediaDialogPage.setMediaFileInput('mediaFile');
        expect(designerIdeaMediaDialogPage.getMediaFileInput()).toMatch('mediaFile');
        designerIdeaMediaDialogPage.getIsShowInput().isSelected().then((selected) => {
            if (selected) {
                designerIdeaMediaDialogPage.getIsShowInput().click();
                expect(designerIdeaMediaDialogPage.getIsShowInput().isSelected()).toBeFalsy();
            } else {
                designerIdeaMediaDialogPage.getIsShowInput().click();
                expect(designerIdeaMediaDialogPage.getIsShowInput().isSelected()).toBeTruthy();
            }
        });
        designerIdeaMediaDialogPage.designerSelectLastOption();
        designerIdeaMediaDialogPage.save();
        expect(designerIdeaMediaDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class DesignerIdeaMediaComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-designer-idea-media div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class DesignerIdeaMediaDialogPage {
    modalTitle = element(by.css('h4#myDesignerIdeaMediaLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));
    shareTimeInput = element(by.css('input#field_shareTime'));
    introductionInput = element(by.css('textarea#field_introduction'));
    mediaFileInput = element(by.css('input#field_mediaFile'));
    isShowInput = element(by.css('input#field_isShow'));
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

    setIntroductionInput = function(introduction) {
        this.introductionInput.sendKeys(introduction);
    };

    getIntroductionInput = function() {
        return this.introductionInput.getAttribute('value');
    };

    setMediaFileInput = function(mediaFile) {
        this.mediaFileInput.sendKeys(mediaFile);
    };

    getMediaFileInput = function() {
        return this.mediaFileInput.getAttribute('value');
    };

    getIsShowInput = function() {
        return this.isShowInput;
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
