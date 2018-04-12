/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { MyFileComponent } from '../../../../../../main/webapp/app/entities/my-file/my-file.component';
import { MyFileService } from '../../../../../../main/webapp/app/entities/my-file/my-file.service';
import { MyFile } from '../../../../../../main/webapp/app/entities/my-file/my-file.model';

describe('Component Tests', () => {

    describe('MyFile Management Component', () => {
        let comp: MyFileComponent;
        let fixture: ComponentFixture<MyFileComponent>;
        let service: MyFileService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [MyFileComponent],
                providers: [
                    MyFileService
                ]
            })
            .overrideTemplate(MyFileComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MyFileComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MyFileService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new MyFile(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.myFiles[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
