/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { MyFileDetailComponent } from '../../../../../../main/webapp/app/entities/my-file/my-file-detail.component';
import { MyFileService } from '../../../../../../main/webapp/app/entities/my-file/my-file.service';
import { MyFile } from '../../../../../../main/webapp/app/entities/my-file/my-file.model';

describe('Component Tests', () => {

    describe('MyFile Management Detail Component', () => {
        let comp: MyFileDetailComponent;
        let fixture: ComponentFixture<MyFileDetailComponent>;
        let service: MyFileService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [MyFileDetailComponent],
                providers: [
                    MyFileService
                ]
            })
            .overrideTemplate(MyFileDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MyFileDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MyFileService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new MyFile(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.myFile).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
