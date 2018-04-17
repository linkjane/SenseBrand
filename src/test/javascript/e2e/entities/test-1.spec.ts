import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Test1 e2e test', () => {

    let navBarPage: NavBarPage;
    let test1DialogPage: Test1DialogPage;
    let test1ComponentsPage: Test1ComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Test1S', () => {
        navBarPage.goToEntity('test-1');
        test1ComponentsPage = new Test1ComponentsPage();
        expect(test1ComponentsPage.getTitle())
            .toMatch(/senseBrandApp.test1.home.title/);

    });

    it('should load create Test1 dialog', () => {
        test1ComponentsPage.clickOnCreateButton();
        test1DialogPage = new Test1DialogPage();
        expect(test1DialogPage.getModalTitle())
            .toMatch(/senseBrandApp.test1.home.createOrEditLabel/);
        test1DialogPage.close();
    });

    it('should create and save Test1S', () => {
        test1ComponentsPage.clickOnCreateButton();
        test1DialogPage.setTestInput('test');
        expect(test1DialogPage.getTestInput()).toMatch('test');
        test1DialogPage.setNInput('n');
        expect(test1DialogPage.getNInput()).toMatch('n');
        test1DialogPage.setSdfInput('sdf');
        expect(test1DialogPage.getSdfInput()).toMatch('sdf');
        test1DialogPage.setImageFileInput('imageFile');
        expect(test1DialogPage.getImageFileInput()).toMatch('imageFile');
        test1DialogPage.setBlobFileInput('blobFile');
        expect(test1DialogPage.getBlobFileInput()).toMatch('blobFile');
        test1DialogPage.setTextFileInput('textFile');
        expect(test1DialogPage.getTextFileInput()).toMatch('textFile');
        test1DialogPage.setTextFileTestInput('textFileTest');
        expect(test1DialogPage.getTextFileTestInput()).toMatch('textFileTest');
        test1DialogPage.save();
        expect(test1DialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class Test1ComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-test-1 div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class Test1DialogPage {
    modalTitle = element(by.css('h4#myTest1Label'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    testInput = element(by.css('input#field_test'));
    nInput = element(by.css('input#field_n'));
    sdfInput = element(by.css('input#field_sdf'));
    imageFileInput = element(by.css('input#field_imageFile'));
    blobFileInput = element(by.css('input#field_blobFile'));
    textFileInput = element(by.css('input#field_textFile'));
    textFileTestInput = element(by.css('textarea#field_textFileTest'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setTestInput = function(test) {
        this.testInput.sendKeys(test);
    };

    getTestInput = function() {
        return this.testInput.getAttribute('value');
    };

    setNInput = function(n) {
        this.nInput.sendKeys(n);
    };

    getNInput = function() {
        return this.nInput.getAttribute('value');
    };

    setSdfInput = function(sdf) {
        this.sdfInput.sendKeys(sdf);
    };

    getSdfInput = function() {
        return this.sdfInput.getAttribute('value');
    };

    setImageFileInput = function(imageFile) {
        this.imageFileInput.sendKeys(imageFile);
    };

    getImageFileInput = function() {
        return this.imageFileInput.getAttribute('value');
    };

    setBlobFileInput = function(blobFile) {
        this.blobFileInput.sendKeys(blobFile);
    };

    getBlobFileInput = function() {
        return this.blobFileInput.getAttribute('value');
    };

    setTextFileInput = function(textFile) {
        this.textFileInput.sendKeys(textFile);
    };

    getTextFileInput = function() {
        return this.textFileInput.getAttribute('value');
    };

    setTextFileTestInput = function(textFileTest) {
        this.textFileTestInput.sendKeys(textFileTest);
    };

    getTextFileTestInput = function() {
        return this.textFileTestInput.getAttribute('value');
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
