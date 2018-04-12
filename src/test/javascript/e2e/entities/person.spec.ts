import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
import * as path from 'path';
describe('Person e2e test', () => {

    let navBarPage: NavBarPage;
    let personDialogPage: PersonDialogPage;
    let personComponentsPage: PersonComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load People', () => {
        navBarPage.goToEntity('person');
        personComponentsPage = new PersonComponentsPage();
        expect(personComponentsPage.getTitle())
            .toMatch(/senseBrandApp.person.home.title/);

    });

    it('should load create Person dialog', () => {
        personComponentsPage.clickOnCreateButton();
        personDialogPage = new PersonDialogPage();
        expect(personDialogPage.getModalTitle())
            .toMatch(/senseBrandApp.person.home.createOrEditLabel/);
        personDialogPage.close();
    });

    it('should create and save People', () => {
        personComponentsPage.clickOnCreateButton();
        personDialogPage.setFile1Input(absolutePath);
        personDialogPage.setFile2Input(absolutePath);
        personDialogPage.setFile3Input('file3');
        expect(personDialogPage.getFile3Input()).toMatch('file3');
        personDialogPage.save();
        expect(personDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class PersonComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-person div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class PersonDialogPage {
    modalTitle = element(by.css('h4#myPersonLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    file1Input = element(by.css('input#file_file1'));
    file2Input = element(by.css('input#file_file2'));
    file3Input = element(by.css('textarea#field_file3'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setFile1Input = function(file1) {
        this.file1Input.sendKeys(file1);
    };

    getFile1Input = function() {
        return this.file1Input.getAttribute('value');
    };

    setFile2Input = function(file2) {
        this.file2Input.sendKeys(file2);
    };

    getFile2Input = function() {
        return this.file2Input.getAttribute('value');
    };

    setFile3Input = function(file3) {
        this.file3Input.sendKeys(file3);
    };

    getFile3Input = function() {
        return this.file3Input.getAttribute('value');
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
