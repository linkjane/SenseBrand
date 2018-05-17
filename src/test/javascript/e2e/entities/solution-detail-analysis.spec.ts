import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('SolutionDetailAnalysis e2e test', () => {

    let navBarPage: NavBarPage;
    let solutionDetailAnalysisDialogPage: SolutionDetailAnalysisDialogPage;
    let solutionDetailAnalysisComponentsPage: SolutionDetailAnalysisComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load SolutionDetailAnalyses', () => {
        navBarPage.goToEntity('solution-detail-analysis');
        solutionDetailAnalysisComponentsPage = new SolutionDetailAnalysisComponentsPage();
        expect(solutionDetailAnalysisComponentsPage.getTitle())
            .toMatch(/senseBrandApp.solutionDetailAnalysis.home.title/);

    });

    it('should load create SolutionDetailAnalysis dialog', () => {
        solutionDetailAnalysisComponentsPage.clickOnCreateButton();
        solutionDetailAnalysisDialogPage = new SolutionDetailAnalysisDialogPage();
        expect(solutionDetailAnalysisDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.solutionDetailAnalysis.home.createOrEditLabel/);
        solutionDetailAnalysisDialogPage.close();
    });

    it('should create and save SolutionDetailAnalyses', () => {
        solutionDetailAnalysisComponentsPage.clickOnCreateButton();
        solutionDetailAnalysisDialogPage.setTitleInput('title');
        expect(solutionDetailAnalysisDialogPage.getTitleInput()).toMatch('title');
        solutionDetailAnalysisDialogPage.setIntroductionInput('introduction');
        expect(solutionDetailAnalysisDialogPage.getIntroductionInput()).toMatch('introduction');
        solutionDetailAnalysisDialogPage.solutionSelectLastOption();
        solutionDetailAnalysisDialogPage.save();
        expect(solutionDetailAnalysisDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SolutionDetailAnalysisComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-solution-detail-analysis div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SolutionDetailAnalysisDialogPage {
    modalTitle = element(by.css('h4#mySolutionDetailAnalysisLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));
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
