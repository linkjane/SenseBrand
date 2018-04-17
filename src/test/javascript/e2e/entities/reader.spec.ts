import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Reader e2e test', () => {

    let navBarPage: NavBarPage;
    let readerDialogPage: ReaderDialogPage;
    let readerComponentsPage: ReaderComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Readers', () => {
        navBarPage.goToEntity('reader');
        readerComponentsPage = new ReaderComponentsPage();
        expect(readerComponentsPage.getTitle())
            .toMatch(/senseBrandApp.reader.home.title/);

    });

    it('should load create Reader dialog', () => {
        readerComponentsPage.clickOnCreateButton();
        readerDialogPage = new ReaderDialogPage();
        expect(readerDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.reader.home.createOrEditLabel/);
        readerDialogPage.close();
    });

    it('should create and save Readers', () => {
        readerComponentsPage.clickOnCreateButton();
        readerDialogPage.setImageFileInput('imageFile');
        expect(readerDialogPage.getImageFileInput()).toMatch('imageFile');
        readerDialogPage.setTextFileInput('textFile');
        expect(readerDialogPage.getTextFileInput()).toMatch('textFile');
        readerDialogPage.setBlobFileInput('blobFile');
        expect(readerDialogPage.getBlobFileInput()).toMatch('blobFile');
        readerDialogPage.save();
        expect(readerDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ReaderComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-reader div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ReaderDialogPage {
    modalTitle = element(by.css('h4#myReaderLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    imageFileInput = element(by.css('input#field_imageFile'));
    textFileInput = element(by.css('textarea#field_textFile'));
    blobFileInput = element(by.css('input#field_blobFile'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setImageFileInput = function(imageFile) {
        this.imageFileInput.sendKeys(imageFile);
    };

    getImageFileInput = function() {
        return this.imageFileInput.getAttribute('value');
    };

    setTextFileInput = function(textFile) {
        this.textFileInput.sendKeys(textFile);
    };

    getTextFileInput = function() {
        return this.textFileInput.getAttribute('value');
    };

    setBlobFileInput = function(blobFile) {
        this.blobFileInput.sendKeys(blobFile);
    };

    getBlobFileInput = function() {
        return this.blobFileInput.getAttribute('value');
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
