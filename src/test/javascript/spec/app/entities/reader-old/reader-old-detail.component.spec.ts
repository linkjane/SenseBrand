/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { ReaderOldDetailComponent } from '../../../../../../main/webapp/app/entities/reader-old/reader-old-detail.component';
import { ReaderOldService } from '../../../../../../main/webapp/app/entities/reader-old/reader-old.service';
import { ReaderOld } from '../../../../../../main/webapp/app/entities/reader-old/reader-old.model';

describe('Component Tests', () => {

    describe('ReaderOld Management Detail Component', () => {
        let comp: ReaderOldDetailComponent;
        let fixture: ComponentFixture<ReaderOldDetailComponent>;
        let service: ReaderOldService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [ReaderOldDetailComponent],
                providers: [
                    ReaderOldService
                ]
            })
            .overrideTemplate(ReaderOldDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReaderOldDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReaderOldService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ReaderOld(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.readerOld).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
