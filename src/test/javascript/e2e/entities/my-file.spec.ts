import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
import * as path from 'path';
describe('MyFile e2e test', () => {

    let navBarPage: NavBarPage;
    let myFileDialogPage: MyFileDialogPage;
    let myFileComponentsPage: MyFileComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load MyFiles', () => {
        navBarPage.goToEntity('my-file');
        myFileComponentsPage = new MyFileComponentsPage();
        expect(myFileComponentsPage.getTitle())
            .toMatch(/senseBrandApp.myFile.home.title/);

    });

    it('should load create MyFile dialog', () => {
        myFileComponentsPage.clickOnCreateButton();
        myFileDialogPage = new MyFileDialogPage();
        expect(myFileDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.myFile.home.createOrEditLabel/);
        myFileDialogPage.close();
    });

    it('should create and save MyFiles', () => {
        myFileComponentsPage.clickOnCreateButton();
        myFileDialogPage.setFilenameInput('filename');
        expect(myFileDialogPage.getFilenameInput()).toMatch('filename');
        myFileDialogPage.setMyFileInput('myFile');
        expect(myFileDialogPage.getMyFileInput()).toMatch('myFile');
        myFileDialogPage.setImageExampleInput(absolutePath);
        myFileDialogPage.setTestExampleInput('testExample');
        expect(myFileDialogPage.getTestExampleInput()).toMatch('testExample');
        myFileDialogPage.save();
        expect(myFileDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class MyFileComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-my-file div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MyFileDialogPage {
    modalTitle = element(by.css('h4#myMyFileLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    filenameInput = element(by.css('input#field_filename'));
    myFileInput = element(by.css('input#field_myFile'));
    imageExampleInput = element(by.css('input#file_imageExample'));
    testExampleInput = element(by.css('textarea#field_testExample'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setFilenameInput = function(filename) {
        this.filenameInput.sendKeys(filename);
    };

    getFilenameInput = function() {
        return this.filenameInput.getAttribute('value');
    };

    setMyFileInput = function(myFile) {
        this.myFileInput.sendKeys(myFile);
    };

    getMyFileInput = function() {
        return this.myFileInput.getAttribute('value');
    };

    setImageExampleInput = function(imageExample) {
        this.imageExampleInput.sendKeys(imageExample);
    };

    getImageExampleInput = function() {
        return this.imageExampleInput.getAttribute('value');
    };

    setTestExampleInput = function(testExample) {
        this.testExampleInput.sendKeys(testExample);
    };

    getTestExampleInput = function() {
        return this.testExampleInput.getAttribute('value');
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
