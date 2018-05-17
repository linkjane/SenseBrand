import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('SolutionDetailAnalysisImg e2e test', () => {

    let navBarPage: NavBarPage;
    let solutionDetailAnalysisImgDialogPage: SolutionDetailAnalysisImgDialogPage;
    let solutionDetailAnalysisImgComponentsPage: SolutionDetailAnalysisImgComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load SolutionDetailAnalysisImgs', () => {
        navBarPage.goToEntity('solution-detail-analysis-img');
        solutionDetailAnalysisImgComponentsPage = new SolutionDetailAnalysisImgComponentsPage();
        expect(solutionDetailAnalysisImgComponentsPage.getTitle())
            .toMatch(/senseBrandApp.solutionDetailAnalysisImg.home.title/);

    });

    it('should load create SolutionDetailAnalysisImg dialog', () => {
        solutionDetailAnalysisImgComponentsPage.clickOnCreateButton();
        solutionDetailAnalysisImgDialogPage = new SolutionDetailAnalysisImgDialogPage();
        expect(solutionDetailAnalysisImgDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.solutionDetailAnalysisImg.home.createOrEditLabel/);
        solutionDetailAnalysisImgDialogPage.close();
    });

    it('should create and save SolutionDetailAnalysisImgs', () => {
        solutionDetailAnalysisImgComponentsPage.clickOnCreateButton();
        solutionDetailAnalysisImgDialogPage.setTitleInput('title');
        expect(solutionDetailAnalysisImgDialogPage.getTitleInput()).toMatch('title');
        solutionDetailAnalysisImgDialogPage.setImgFileInput('imgFile');
        expect(solutionDetailAnalysisImgDialogPage.getImgFileInput()).toMatch('imgFile');
        solutionDetailAnalysisImgDialogPage.setIntroductionInput('introduction');
        expect(solutionDetailAnalysisImgDialogPage.getIntroductionInput()).toMatch('introduction');
        solutionDetailAnalysisImgDialogPage.detailAnalysisSelectLastOption();
        solutionDetailAnalysisImgDialogPage.save();
        expect(solutionDetailAnalysisImgDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SolutionDetailAnalysisImgComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-solution-detail-analysis-img div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SolutionDetailAnalysisImgDialogPage {
    modalTitle = element(by.css('h4#mySolutionDetailAnalysisImgLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));
    imgFileInput = element(by.css('input#field_imgFile'));
    introductionInput = element(by.css('textarea#field_introduction'));
    detailAnalysisSelect = element(by.css('select#field_detailAnalysis'));

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

    detailAnalysisSelectLastOption = function() {
        this.detailAnalysisSelect.all(by.tagName('option')).last().click();
    };

    detailAnalysisSelectOption = function(option) {
        this.detailAnalysisSelect.sendKeys(option);
    };

    getDetailAnalysisSelect = function() {
        return this.detailAnalysisSelect;
    };

    getDetailAnalysisSelectedOption = function() {
        return this.detailAnalysisSelect.element(by.css('option:checked')).getText();
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
