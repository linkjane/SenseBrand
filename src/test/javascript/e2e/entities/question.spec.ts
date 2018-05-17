import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Question e2e test', () => {

    let navBarPage: NavBarPage;
    let questionDialogPage: QuestionDialogPage;
    let questionComponentsPage: QuestionComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Questions', () => {
        navBarPage.goToEntity('question');
        questionComponentsPage = new QuestionComponentsPage();
        expect(questionComponentsPage.getTitle())
            .toMatch(/senseBrandApp.question.home.title/);

    });

    it('should load create Question dialog', () => {
        questionComponentsPage.clickOnCreateButton();
        questionDialogPage = new QuestionDialogPage();
        expect(questionDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.question.home.createOrEditLabel/);
        questionDialogPage.close();
    });

    it('should create and save Questions', () => {
        questionComponentsPage.clickOnCreateButton();
        questionDialogPage.setNameInput('name');
        expect(questionDialogPage.getNameInput()).toMatch('name');
        questionDialogPage.setContentInput('content');
        expect(questionDialogPage.getContentInput()).toMatch('content');
        questionDialogPage.save();
        expect(questionDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class QuestionComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-question div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class QuestionDialogPage {
    modalTitle = element(by.css('h4#myQuestionLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    contentInput = element(by.css('textarea#field_content'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    };

    setContentInput = function(content) {
        this.contentInput.sendKeys(content);
    };

    getContentInput = function() {
        return this.contentInput.getAttribute('value');
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
