import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('SolutionCorrelation e2e test', () => {

    let navBarPage: NavBarPage;
    let solutionCorrelationDialogPage: SolutionCorrelationDialogPage;
    let solutionCorrelationComponentsPage: SolutionCorrelationComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load SolutionCorrelations', () => {
        navBarPage.goToEntity('solution-correlation');
        solutionCorrelationComponentsPage = new SolutionCorrelationComponentsPage();
        expect(solutionCorrelationComponentsPage.getTitle())
            .toMatch(/senseBrandApp.solutionCorrelation.home.title/);

    });

    it('should load create SolutionCorrelation dialog', () => {
        solutionCorrelationComponentsPage.clickOnCreateButton();
        solutionCorrelationDialogPage = new SolutionCorrelationDialogPage();
        expect(solutionCorrelationDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.solutionCorrelation.home.createOrEditLabel/);
        solutionCorrelationDialogPage.close();
    });

    it('should create and save SolutionCorrelations', () => {
        solutionCorrelationComponentsPage.clickOnCreateButton();
        solutionCorrelationDialogPage.setImgFileInput('imgFile');
        expect(solutionCorrelationDialogPage.getImgFileInput()).toMatch('imgFile');
        solutionCorrelationDialogPage.setGoLinkInput('goLink');
        expect(solutionCorrelationDialogPage.getGoLinkInput()).toMatch('goLink');
        solutionCorrelationDialogPage.solutionSelectLastOption();
        solutionCorrelationDialogPage.save();
        expect(solutionCorrelationDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SolutionCorrelationComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-solution-correlation div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SolutionCorrelationDialogPage {
    modalTitle = element(by.css('h4#mySolutionCorrelationLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    imgFileInput = element(by.css('input#field_imgFile'));
    goLinkInput = element(by.css('input#field_goLink'));
    solutionSelect = element(by.css('select#field_solution'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setImgFileInput = function(imgFile) {
        this.imgFileInput.sendKeys(imgFile);
    };

    getImgFileInput = function() {
        return this.imgFileInput.getAttribute('value');
    };

    setGoLinkInput = function(goLink) {
        this.goLinkInput.sendKeys(goLink);
    };

    getGoLinkInput = function() {
        return this.goLinkInput.getAttribute('value');
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
