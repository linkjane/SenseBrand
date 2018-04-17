import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
import * as path from 'path';
describe('ReaderOld e2e test', () => {

    let navBarPage: NavBarPage;
    let readerOldDialogPage: ReaderOldDialogPage;
    let readerOldComponentsPage: ReaderOldComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ReaderOlds', () => {
        navBarPage.goToEntity('reader-old');
        readerOldComponentsPage = new ReaderOldComponentsPage();
        expect(readerOldComponentsPage.getTitle())
            .toMatch(/senseBrandApp.readerOld.home.title/);

    });

    it('should load create ReaderOld dialog', () => {
        readerOldComponentsPage.clickOnCreateButton();
        readerOldDialogPage = new ReaderOldDialogPage();
        expect(readerOldDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.readerOld.home.createOrEditLabel/);
        readerOldDialogPage.close();
    });

    it('should create and save ReaderOlds', () => {
        readerOldComponentsPage.clickOnCreateButton();
        readerOldDialogPage.setImageFileInput(absolutePath);
        readerOldDialogPage.setTextFileInput('textFile');
        expect(readerOldDialogPage.getTextFileInput()).toMatch('textFile');
        readerOldDialogPage.setBlobFileInput(absolutePath);
        readerOldDialogPage.setLocalTimeInput('2000-12-31');
        expect(readerOldDialogPage.getLocalTimeInput()).toMatch('2000-12-31');
        readerOldDialogPage.save();
        expect(readerOldDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ReaderOldComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-reader-old div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ReaderOldDialogPage {
    modalTitle = element(by.css('h4#myReaderOldLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    imageFileInput = element(by.css('input#file_imageFile'));
    textFileInput = element(by.css('textarea#field_textFile'));
    blobFileInput = element(by.css('input#file_blobFile'));
    localTimeInput = element(by.css('input#field_localTime'));

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

    setLocalTimeInput = function(localTime) {
        this.localTimeInput.sendKeys(localTime);
    };

    getLocalTimeInput = function() {
        return this.localTimeInput.getAttribute('value');
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
