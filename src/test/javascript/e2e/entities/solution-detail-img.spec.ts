import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('SolutionDetailImg e2e test', () => {

    let navBarPage: NavBarPage;
    let solutionDetailImgDialogPage: SolutionDetailImgDialogPage;
    let solutionDetailImgComponentsPage: SolutionDetailImgComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load SolutionDetailImgs', () => {
        navBarPage.goToEntity('solution-detail-img');
        solutionDetailImgComponentsPage = new SolutionDetailImgComponentsPage();
        expect(solutionDetailImgComponentsPage.getTitle())
            .toMatch(/senseBrandApp.solutionDetailImg.home.title/);

    });

    it('should load create SolutionDetailImg dialog', () => {
        solutionDetailImgComponentsPage.clickOnCreateButton();
        solutionDetailImgDialogPage = new SolutionDetailImgDialogPage();
        expect(solutionDetailImgDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.solutionDetailImg.home.createOrEditLabel/);
        solutionDetailImgDialogPage.close();
    });

    it('should create and save SolutionDetailImgs', () => {
        solutionDetailImgComponentsPage.clickOnCreateButton();
        solutionDetailImgDialogPage.setTitleInput('title');
        expect(solutionDetailImgDialogPage.getTitleInput()).toMatch('title');
        solutionDetailImgDialogPage.setImgFileInput('imgFile');
        expect(solutionDetailImgDialogPage.getImgFileInput()).toMatch('imgFile');
        solutionDetailImgDialogPage.setIntroductionInput('introduction');
        expect(solutionDetailImgDialogPage.getIntroductionInput()).toMatch('introduction');
        solutionDetailImgDialogPage.solutionSelectLastOption();
        solutionDetailImgDialogPage.save();
        expect(solutionDetailImgDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SolutionDetailImgComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-solution-detail-img div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SolutionDetailImgDialogPage {
    modalTitle = element(by.css('h4#mySolutionDetailImgLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));
    imgFileInput = element(by.css('input#field_imgFile'));
    introductionInput = element(by.css('textarea#field_introduction'));
    solutionSelect = element(by.css('select#field_solution'));

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

    solutionSelectLastOption = function() {
        this.solutionSelect.all(by.tagName('option')).last().click();
    };

    solutionSelectOption = function(option) {
        this.solutionSelect.sendKeys(option);
    };

    getSolutionSelect = function() {
        return this.solutionSelect;
    };

    getSolutionSelectedOption = function() {
        return this.solutionSelect.element(by.css('option:checked')).getText();
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
