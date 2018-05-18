import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Solution e2e test', () => {

    let navBarPage: NavBarPage;
    let solutionDialogPage: SolutionDialogPage;
    let solutionComponentsPage: SolutionComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Solutions', () => {
        navBarPage.goToEntity('solution');
        solutionComponentsPage = new SolutionComponentsPage();
        expect(solutionComponentsPage.getTitle())
            .toMatch(/senseBrandApp.solution.home.title/);

    });

    it('should load create Solution dialog', () => {
        solutionComponentsPage.clickOnCreateButton();
        solutionDialogPage = new SolutionDialogPage();
        expect(solutionDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.solution.home.createOrEditLabel/);
        solutionDialogPage.close();
    });

    it('should create and save Solutions', () => {
        solutionComponentsPage.clickOnCreateButton();
        solutionDialogPage.setTitleInput('title');
        expect(solutionDialogPage.getTitleInput()).toMatch('title');
        solutionDialogPage.setIntroductionInput('introduction');
        expect(solutionDialogPage.getIntroductionInput()).toMatch('introduction');
        solutionDialogPage.setBannerImgFileInput('bannerImgFile');
        expect(solutionDialogPage.getBannerImgFileInput()).toMatch('bannerImgFile');
        solutionDialogPage.setBannerIntroductionInput('bannerIntroduction');
        expect(solutionDialogPage.getBannerIntroductionInput()).toMatch('bannerIntroduction');
        solutionDialogPage.setSummarizeTitleInput('summarizeTitle');
        expect(solutionDialogPage.getSummarizeTitleInput()).toMatch('summarizeTitle');
        solutionDialogPage.setSummarizeImgFileInput('summarizeImgFile');
        expect(solutionDialogPage.getSummarizeImgFileInput()).toMatch('summarizeImgFile');
        solutionDialogPage.setSummarizeContentInput('summarizeContent');
        expect(solutionDialogPage.getSummarizeContentInput()).toMatch('summarizeContent');
        solutionDialogPage.setSignificanceTitleInput('significanceTitle');
        expect(solutionDialogPage.getSignificanceTitleInput()).toMatch('significanceTitle');
        solutionDialogPage.setSignificanceImgFileInput('significanceImgFile');
        expect(solutionDialogPage.getSignificanceImgFileInput()).toMatch('significanceImgFile');
        solutionDialogPage.setSignificanceIntroductionInput('significanceIntroduction');
        expect(solutionDialogPage.getSignificanceIntroductionInput()).toMatch('significanceIntroduction');
        solutionDialogPage.setDetailAnalysisTitleInput('detailAnalysisTitle');
        expect(solutionDialogPage.getDetailAnalysisTitleInput()).toMatch('detailAnalysisTitle');
        solutionDialogPage.getIsShowInput().isSelected().then((selected) => {
            if (selected) {
                solutionDialogPage.getIsShowInput().click();
                expect(solutionDialogPage.getIsShowInput().isSelected()).toBeFalsy();
            } else {
                solutionDialogPage.getIsShowInput().click();
                expect(solutionDialogPage.getIsShowInput().isSelected()).toBeTruthy();
            }
        });
        solutionDialogPage.save();
        expect(solutionDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SolutionComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-solution div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SolutionDialogPage {
    modalTitle = element(by.css('h4#mySolutionLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));
    introductionInput = element(by.css('textarea#field_introduction'));
    bannerImgFileInput = element(by.css('input#field_bannerImgFile'));
    bannerIntroductionInput = element(by.css('textarea#field_bannerIntroduction'));
    summarizeTitleInput = element(by.css('input#field_summarizeTitle'));
    summarizeImgFileInput = element(by.css('input#field_summarizeImgFile'));
    summarizeContentInput = element(by.css('textarea#field_summarizeContent'));
    significanceTitleInput = element(by.css('input#field_significanceTitle'));
    significanceImgFileInput = element(by.css('input#field_significanceImgFile'));
    significanceIntroductionInput = element(by.css('textarea#field_significanceIntroduction'));
    detailAnalysisTitleInput = element(by.css('input#field_detailAnalysisTitle'));
    isShowInput = element(by.css('input#field_isShow'));

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

    setBannerImgFileInput = function(bannerImgFile) {
        this.bannerImgFileInput.sendKeys(bannerImgFile);
    };

    getBannerImgFileInput = function() {
        return this.bannerImgFileInput.getAttribute('value');
    };

    setBannerIntroductionInput = function(bannerIntroduction) {
        this.bannerIntroductionInput.sendKeys(bannerIntroduction);
    };

    getBannerIntroductionInput = function() {
        return this.bannerIntroductionInput.getAttribute('value');
    };

    setSummarizeTitleInput = function(summarizeTitle) {
        this.summarizeTitleInput.sendKeys(summarizeTitle);
    };

    getSummarizeTitleInput = function() {
        return this.summarizeTitleInput.getAttribute('value');
    };

    setSummarizeImgFileInput = function(summarizeImgFile) {
        this.summarizeImgFileInput.sendKeys(summarizeImgFile);
    };

    getSummarizeImgFileInput = function() {
        return this.summarizeImgFileInput.getAttribute('value');
    };

    setSummarizeContentInput = function(summarizeContent) {
        this.summarizeContentInput.sendKeys(summarizeContent);
    };

    getSummarizeContentInput = function() {
        return this.summarizeContentInput.getAttribute('value');
    };

    setSignificanceTitleInput = function(significanceTitle) {
        this.significanceTitleInput.sendKeys(significanceTitle);
    };

    getSignificanceTitleInput = function() {
        return this.significanceTitleInput.getAttribute('value');
    };

    setSignificanceImgFileInput = function(significanceImgFile) {
        this.significanceImgFileInput.sendKeys(significanceImgFile);
    };

    getSignificanceImgFileInput = function() {
        return this.significanceImgFileInput.getAttribute('value');
    };

    setSignificanceIntroductionInput = function(significanceIntroduction) {
        this.significanceIntroductionInput.sendKeys(significanceIntroduction);
    };

    getSignificanceIntroductionInput = function() {
        return this.significanceIntroductionInput.getAttribute('value');
    };

    setDetailAnalysisTitleInput = function(detailAnalysisTitle) {
        this.detailAnalysisTitleInput.sendKeys(detailAnalysisTitle);
    };

    getDetailAnalysisTitleInput = function() {
        return this.detailAnalysisTitleInput.getAttribute('value');
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