/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { ReaderDetailComponent } from '../../../../../../main/webapp/app/entities/reader/reader-detail.component';
import { ReaderService } from '../../../../../../main/webapp/app/entities/reader/reader.service';
import { Reader } from '../../../../../../main/webapp/app/entities/reader/reader.model';

describe('Component Tests', () => {

    describe('Reader Management Detail Component', () => {
        let comp: ReaderDetailComponent;
        let fixture: ComponentFixture<ReaderDetailComponent>;
        let service: ReaderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [ReaderDetailComponent],
                providers: [
                    ReaderService
                ]
            })
            .overrideTemplate(ReaderDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReaderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReaderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Reader(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.reader).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
